import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationOperation extends AbstractDatabaseOperation {
    private RegistrationInfo _registrationInfo;

    /**
     * Creates a new <code>RegistrationOperation</code> instance.
     * 
     */
    public RegistrationOperation(RegistrationInfo registrationInfo) {
        super("sql-register", true);
        _registrationInfo = registrationInfo;
    }

    @Override
    public void populateStatement(PreparedStatement statement)
        throws SQLException {
        int index = 1;
        statement.setString(index++, _registrationInfo.getEmail());
        Date date = new Date(_registrationInfo.getDateOfBirth().getTime());
        statement.setDate(index++, date);
        String digest = DigestUtils.getDigest(_registrationInfo.getPassword());
        statement.setString(index++, digest);
        statement.setString(index++, _registrationInfo.getCountry().name());
        statement.setString(index++, "NO");
        // Default display name
        statement.setString(index, _registrationInfo.getDisplayName());
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}