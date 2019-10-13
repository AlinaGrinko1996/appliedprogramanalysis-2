import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveBlockedOperation extends AbstractDatabaseOperation {
    private int _memberId;
    private int _startPage;
    private List<User> _users;
    private int _max;

    /**
     * Creates a new <code>RetrieveBlockedOperation</code> instance.
     */
    public RetrieveBlockedOperation(String sql, int memberId, int startPage, int max) {
        super(sql);
        _memberId = memberId;
        _startPage = startPage;
        _users = new ArrayList<>();
        _max = max;
    }

    /**
     * Describe <code>populateStatement</code> method here.
     *
     * @param preparedStatement a <code>PreparedStatement</code> value
     * @exception SQLException if an error occurs
     */
    @Override
    public final void populateStatement(
        final PreparedStatement preparedStatement) throws SQLException {
        int index = 1;
        preparedStatement.setInt(index++, _memberId);
        preparedStatement.setInt(index++, _startPage);
        preparedStatement.setInt(index++, _max);
    }

}
