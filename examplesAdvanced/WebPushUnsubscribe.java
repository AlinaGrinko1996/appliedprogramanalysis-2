import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WebPushUnsubscribe extends AbstractDatabaseOperation {
    private int memberId;
    private String tokenId;

    public WebPushUnsubscribe(int memberId, String tokenId) {
        super("sql-web-push-unsubscribe", true);
        this.tokenId = tokenId;
        this.memberId = memberId;
    }

    @Override
    public final void populateStatement(final PreparedStatement statement) throws SQLException {
        int index = 1;
        statement.setInt(index++, memberId);
        statement.setString(index, tokenId);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}