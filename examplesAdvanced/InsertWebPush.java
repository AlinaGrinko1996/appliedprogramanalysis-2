import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertWebPush extends AbstractDatabaseOperation {
    private int memberId;
    private String tokenId;
    private String deviceType;
    private String browserType;


    public InsertWebPush(int memberId, String tokenId, String deviceType,
                         String browserType) {
        super("sql-insert-web-push", true);
        this.memberId = memberId;
        this.tokenId = tokenId;
        this.deviceType = deviceType;
        this.browserType = browserType;
    }

    @Override
    public final void populateStatement(final PreparedStatement statement)
            throws SQLException {
        int index = 1;
        statement.setInt(index++, memberId);
        statement.setString(index++, tokenId);
        statement.setString(index++, deviceType);
        statement.setString(index, browserType);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}