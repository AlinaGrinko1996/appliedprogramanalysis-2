import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteBlog extends AbstractDatabaseOperation {
    private int _blogId;

    /**
     * Creates a new <code>DeleteBlog</code> instance.
     *
     * @param blogId an <code>int</code> value
     */
    public DeleteBlog(int blogId) {
        super("sql-delete-blog", true);
        _blogId = blogId;
    }

    @Override
    public final void populateStatement(final PreparedStatement statement)
        throws SQLException {
        statement.setInt(1, _blogId);
    }

    @Override
    public void processResults(ResultSet resultSet) throws SQLException {
    }
}
