import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertIpTable extends AbstractDatabaseOperation {
    private int _memberId;
    private String _ipAddress;

    /**
     * Creates a new <code>InsertIntoIpTable</code> instance.
     */
    public InsertIpTable(int memberId, String ipAddress) {
        super("sql-insert-iptable", true);
        _memberId = memberId;
        _ipAddress = ipAddress;
    }

    @Override
    public final void populateStatement(final PreparedStatement statement)
        throws SQLException {
        int index = 1;
        statement.setInt(index++, _memberId);
        statement.setString(index++, _ipAddress);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}