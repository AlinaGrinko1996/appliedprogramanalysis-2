import java.sql.*;


public class RetrieveDisplayNameOperation extends AbstractDatabaseOperation {
    private boolean _exists;
    private String _name;

    public RetrieveDisplayNameOperation(String name) {
        super("sql-check-display-name");
        _name = name;
    }

    @Override
    public final void populateStatement(PreparedStatement preparedStatement) throws SQLException {
        int index = 1;
        preparedStatement.setString(index, _name);
    }

    @Override
    public final void processResults(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            _exists = resultSet.first();
        }
    }

    public boolean exists() {
        return _exists;
    }
}
