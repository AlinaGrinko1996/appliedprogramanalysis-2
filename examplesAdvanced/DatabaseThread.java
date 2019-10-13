import java.net.SocketException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException;

public class DatabaseThread extends Thread {
    private static final int MAX_THREADS = 100;
    private static final int THRESHOLD = 100;
    private static final int TIMEOUT = 60;
    public static final long ONE_MINUTE = TimeUnit.MINUTES.toMillis(1);
    private static int _numberOfThreads = 1;
    private final boolean _canFinish;
    private static long _lastCreated;
    private final Map<String, PreparedStatement> _statements;

    private final BlockingQueue<DatabaseOperation> _queue;
    public static final Logger LOG = LogUtils.getLogger(DatabaseThread.class);
    private static final Object LOCK = new Object();

    private volatile static boolean _supportsBatchUpdates;
    static {
        try {
            _supportsBatchUpdates = supportsBatchUpdates();
            if (!_supportsBatchUpdates) {
                LOG.warning("Batch updates aren't supported by this database");
            }
        } catch (final SQLException e) {
            LOG.warning("Failed to obtain a connection", e);
        }
    }

    /**
     * Creates a new <code>DatabaseThread</code> instance.
     */
    public DatabaseThread(final BlockingQueue<DatabaseOperation> queue,
        final boolean canFinish, final String name) {
        super(name);
        _canFinish = canFinish;
        _statements = new HashMap<>();
        setDaemon(true);
        _queue = queue;
    }

    // Implementation of java.lang.Runnable

    /**
     * Describe <code>run</code> method here.
     */
    @Override
    public final void run() {
        Connection conn = null;
        ResultSet resultSet = null;
        final Properties dbProps = DatabaseUtils.getSqlProperties();
        while (true) {
            try {
                DatabaseOperation dbOperation = _queue.poll(TIMEOUT, TimeUnit.SECONDS);
                while (dbOperation != null) {
                    boolean retry = false;
                    final boolean isTransaction = dbOperation.isTransaction();
                    boolean isRollingBack = false;
                    try {
                        if (conn == null) {
                            try {
                                conn = DatabaseUtils.getDatabaseConnection();
                            } catch (final SQLException e) {
                                LOG.warning("Failed to obtain a connection", e);
                                break;
                            }
                        }
                        final List<DatabaseOperation> operationList;
                        if (isTransaction) {
                            conn.setAutoCommit(false);
                            operationList = dbOperation.getDatabaseOperations();
                        } else {
                            operationList = Collections.singletonList(dbOperation);
                        }

                        for (final DatabaseOperation operation : operationList) {
                            PreparedStatement statement = null;
                            String sql = null;
                            try {
                                final boolean useDynamicSql = operation.useDynamicSql();
                                final boolean useReturnGeneratedKeys = dbOperation.useReturnGeneratedKeys();

                                if (useDynamicSql) {
                                    sql = operation.getDynamicSql();
                                } else {
                                    final String resource = operation.getSqlResource();
                                    sql = dbProps.getProperty(resource);
                                    statement = _statements.get(sql);
                                }
                                if (statement == null) {
                                    if (useReturnGeneratedKeys) {
                                        statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                    } else {
                                        statement = conn.prepareStatement(sql);
                                    }
                                    // do not cache dynamic sql queries
                                    if (!useDynamicSql) {
                                        _statements.put(sql, statement);
                                    }
                                }
                                operation.populateStatement(statement);
                                if (LOG.isFine()) {
                                    if (useDynamicSql) {
                                        LOG.fine("Running SQL: " + statement);
                                    } else {
                                        final String resource = operation.getSqlResource();
                                        LOG.fine("Running SQL: " + resource + "=" + statement);
                                    }
                                }
                            } catch (final SQLException e) {
                                isRollingBack = true;
                                LOG.warning("Failed to create statement: " + sql, e);
                                final Throwable cause = e.getCause();
                                if (cause instanceof SocketException
                                        || (cause instanceof MySQLNonTransientConnectionException)) {
                                    retry = true;
                                    LOG.warning("Socket exception, retrying");
                                }
                                break;
                            }
                            if (statement != null) {
                                try {
                                    if (operation.useExecuteUpdate()) {
                                        executeUpdate(operation, statement);
                                    } else {
                                        resultSet = statement.executeQuery();
                                        operation.processResults(statement, resultSet);
                                    }
                                    statement.clearParameters();
                                } catch (final MySQLTransactionRollbackException e) {
                                    LOG.warning("Deadlock detected, retrying: " + statement, e);
                                    retry = true;
                                    isRollingBack = true;
                                    break;
                                } catch (final MySQLNonTransientConnectionException e) {
                                    if (sql != null) {
                                        _statements.remove(sql);
                                    }
                                    LOG.warning("Statement was closed, retrying" + statement, e);
                                    retry = true;
                                    isRollingBack = true;
                                    break;
                                } catch (final SQLException e) {
                                    LOG.warning("Failed to execute statement:" + operation.getSqlResource()
                                            + " - " + statement, e);
                                    isRollingBack = true;
                                    operation.handleExecuteException(e);
                                    operation.setStatementString(statement.toString());
                                    break;
                                }
                            }
                        }
                        if (isTransaction) {
                            if (isRollingBack) {
                                conn.rollback();
                            } else {
                                conn.commit();
                            }
                        }
                    } finally {
                        try {
                            if (resultSet != null) {
                                try {
                                    resultSet.close();
                                } catch (final SQLException e) {
                                    LOG.warning("Failed to close result set", e);
                                }
                            }
                        } finally {
                            if (isTransaction) {
                                conn.setAutoCommit(true);
                            }
                            if (!retry) {
                                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                                synchronized (dbOperation) {
                                    dbOperation.setComplete(true);
                                    dbOperation.notify();
                                }
                            }
                        }
                    }
                    if (!retry) {
                        dbOperation = _queue.poll(TIMEOUT, TimeUnit.SECONDS);
                    }

                    boolean createNew = false;
                    String name = null;

                    if (!_canFinish && (_queue.size() > THRESHOLD)) {
                        synchronized (LOCK) {
                            final long now = System.currentTimeMillis();
                            createNew = (_numberOfThreads < MAX_THREADS) && (now - _lastCreated) > ONE_MINUTE;
                            if (createNew) {
                                name = "Database Thread - " + _numberOfThreads++;
                                _lastCreated = now;
                            }
                        }
                    }
                    if (createNew) {
                        final boolean canFinish = true;
                        final Thread thread = new DatabaseThread(_queue, canFinish, name);
                        thread.start();
                    }
                }
                if (_canFinish) {
                    break;
                }
            } catch (final InterruptedException ignored) {
            } catch (final Throwable t) {
                LOG.warning("Fatal error running sql query", t);
            } finally {
                // close all statements
                final Collection<PreparedStatement> statements = _statements.values();
                for (final PreparedStatement statement : statements) {
                    try {
                        statement.close();
                    } catch (final SQLException e) {
                        LOG.warning("Failed to close statement", e);
                    }
                }
                _statements.clear();
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (final SQLException e) {
                        LOG.warning("Failed to close connection", e);
                    }
                    conn = null;
                }
            }
        }
        synchronized (LOCK) {
            _numberOfThreads--;
        }
    }

    private void executeUpdate(final DatabaseOperation dbOperation, final PreparedStatement statement)
            throws SQLException {
        if (dbOperation.useExecuteBatch()) {
            if (_supportsBatchUpdates) {
                final int[] batchResult = statement.executeBatch();
                int updatedRowsSum = 0;
                for (final int updatedRows : batchResult) {
                    updatedRowsSum += updatedRows;
                }
                dbOperation.setUpdatedRows(updatedRowsSum);
            } else {
                LOG.warning("Failed to execute batch update operation (unsupported)");
            }
        } else {
            final boolean useReturnGeneratedKeys = dbOperation.useReturnGeneratedKeys();
            if (useReturnGeneratedKeys) {
                int updatedRows = statement.executeUpdate();
                if (updatedRows != 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        dbOperation.setGeneratedKey(generatedKeys.getInt(1));
                    }
                }
            } else {
                dbOperation.setUpdatedRows(statement.executeUpdate());
            }
        }
    }

    private static boolean supportsBatchUpdates() throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseUtils.getDatabaseConnection();
            final DatabaseMetaData metaData = connection.getMetaData();
            return metaData.supportsBatchUpdates();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                    LOG.warning("Failed to close connection", e);
                }
            }
        }
    }
}
