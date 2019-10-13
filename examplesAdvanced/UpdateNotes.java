import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateNotes extends AbstractDatabaseOperation {
    private String _note;
    private String _key;
    
    /**
     * Creates a new <code>UpdateNotes</code> instance.
     */
    public UpdateNotes(String note, String key) {
        super("sql-set-note", true);
        _note = note;
        _key = key;
    }

    @Override
    public final void populateStatement(final PreparedStatement statement)
        throws SQLException {
        int index = 1;
        statement.setString(index++, _note);
        statement.setString(index++, _key);
        statement.setString(index++, _note);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}
