import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteChatOperation extends AbstractDatabaseOperation {

    /**
     * Creates a new <code>DeleteFriendOperation</code> instance.
     */
    public DeleteChatOperation() {
        super("sql-delete-chat", true);
    }

    /**
     * Describe <code>populateStatement</code> method here.
     *
     * @param preparedStatement a <code>PreparedStatement</code> value
     * @exception SQLException if an error occurs
     */
    @Override
    public final void populateStatement(PreparedStatement preparedStatement)
        throws SQLException {
        int index = 1;
    }

    /**
     * Describe <code>processResults</code> method here.
     *
     * @param resultSet a <code>ResultSet</code> value
     * @exception SQLException if an error occurs
     */
    @Override
    public final void processResults(final ResultSet resultSet)
        throws SQLException {

    }
}
