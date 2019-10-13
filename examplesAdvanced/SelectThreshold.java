import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectThreshold extends AbstractDatabaseOperation {
    private Threshold _threshold;
    private User _user;
    private Type _type;

    /**
     * Creates a new <code>SelectThreshold</code> instance.
     * @param user an <code>User</code> value
     * @param type a <code>Threshold.Type</code> value
     */
    public SelectThreshold(User user, Type type) {
        super("sql-select-threshold");
        _threshold = new Threshold();
        _user = user;
        _type = type;
        _threshold.setThresholdType(type);
    }

    /**
     * Describe <code>populateStatement</code> method here.
     *
     * @param statement a <code>PreparedStatement</code> value
     * @exception SQLException if an error occurs
     */
    @Override
    public final void populateStatement(final PreparedStatement statement)
        throws SQLException {
        statement.setInt(1, _user.ordinal());
        statement.setString(2, _type.name());
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
            while (resultSet.next()) {
                int index = 1;
                _threshold.setThreshold(resultSet.getInt(index++));
                _threshold.setRate(resultSet.getInt(index++));
            }
        }
    }

    public Threshold getThreshold() {
        return _threshold;
    }
}
