import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionExists extends AbstractDatabaseOperation {
    private Transaction _transaction;
    private boolean _transactionExists;

    /**
     * Creates a new <code>CountAchReturn</code> instance.
     */
    public TransactionExists(Transaction transaction) {
        super("sql-count-ach-return");
        _transaction = transaction;
    }

    @Override
    public void populateStatement(PreparedStatement statement)
        throws SQLException {
        statement.setString(1, _transaction.getTransId());
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            if (resultSet.first()) {
                int count = resultSet.getInt(1);
                _transactionExists = count > 0;
            }
        }
    }

    public boolean transactionExists() {
        return _transactionExists;
    }
}
