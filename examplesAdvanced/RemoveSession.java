import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveSession extends AbstractDatabaseOperation {
    private String _sessionId;

    /**
     * Creates a new <code>RemoveSession</code> instance.
     */
    public RemoveSession(String sessionId) {
        super("sql-remove-session", true);
        _sessionId = sessionId;
    }

    @Override
    public final void populateStatement(final PreparedStatement statement)
        throws SQLException {
        int index = 1;
        statement.setString(index++, _sessionId);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}
