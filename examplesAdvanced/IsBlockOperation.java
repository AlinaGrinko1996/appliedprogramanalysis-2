
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsBlockOperation extends AbstractDatabaseOperation {
    private boolean _blocked;


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
        preparedStatement.setInt(index++, _memberId);
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
        if (resultSet != null) {
            _blocked = resultSet.first();
        }
    }

    public final boolean isBlocked() {
        return _blocked;
    }
}
