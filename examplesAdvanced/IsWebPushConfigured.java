import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsWebPushConfigured extends AbstractDatabaseOperation {
    private int memberId;
    private boolean configured;

    public IsWebPushConfigured(int memberId) {
        super("sql-is-web-push-configured");
        this.memberId = memberId;
    }

    @Override
    public void populateStatement(PreparedStatement statement) throws SQLException {
        statement.setInt(1, this.memberId);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
        this.configured = (resultSet != null) && resultSet.next();
    }

    public boolean isConfigured() {
        return configured;
    }
}