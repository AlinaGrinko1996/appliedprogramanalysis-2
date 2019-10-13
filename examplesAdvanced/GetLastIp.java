import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetLastIp extends AbstractDatabaseOperation {
    private int _memberId;
    private String _ipAddress;

    /**
     * Creates a new <code>GetLastIp</code> instance.
     */
    public GetLastIp(int memberId) {
        super("sql-get-last-ip");
        _memberId = memberId;
    }

    @Override
    public final void populateStatement(PreparedStatement statement)
        throws SQLException {
        int index = 1;
        statement.setInt(index++, _memberId);
    }

    @Override
    public final void processResults(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            if (resultSet.first()) {
                int index = 1;
                _ipAddress = resultSet.getString(index++);
            }
        }
    }

    public String getIpAddress() {
        return _ipAddress;
    }
}
