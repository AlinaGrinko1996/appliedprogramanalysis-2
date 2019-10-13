import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseOperation {
    public String getSqlResource();

    public void populateStatement(PreparedStatement statement) throws SQLException;

    public void processResults(PreparedStatement statement, ResultSet resultSet) throws SQLException;

    public void processResults(ResultSet resultSet) throws SQLException;

    public boolean useExecuteUpdate();

    public boolean useExecuteBatch();

    public boolean useDynamicSql();

    public String getDynamicSql();

    public void setComplete(boolean complete);

    public boolean isComplete();

    public void handleExecuteException(SQLException e);

    public int getUpdatedRows();

    public void setUpdatedRows(final int updatedRows);

    public long getTimeout();

    public SQLException getSQLException();

    public boolean isError();

    public void setStatementString(final String statementString);

    public String getStatementString();

    public boolean isTransaction();

    public List<DatabaseOperation> getDatabaseOperations();

    public boolean useReturnGeneratedKeys();

    public void setGeneratedKey(int key);

    public int getGeneratedKey();
}
