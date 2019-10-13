import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RetrieveSingleBlog extends RetrieveBlogs {
    private int _blogId;

    /**
     * Creates a new <code>RetrieveSingleBlog</code> instance.
     */
    public RetrieveSingleBlog(int blogId) {
        super("sql-retrieve-single-blog");
        _blogId = blogId;
    }

    @Override
    public final void populateStatement(
        final PreparedStatement preparedStatement) throws SQLException {

        int index = 1;
        preparedStatement.setInt(index++, _blogId);
    }
}