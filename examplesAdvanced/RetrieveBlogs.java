import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveBlogs extends AbstractDatabaseOperation {
    private int _startBlog;
    private int _memberId;
    private int _max;
    private List<Blog> _blogs;

    public RetrieveBlogs(String sql, int memberId, int startBlog, int max) {
        super(sql);
        _startBlog = startBlog;
        _memberId = memberId;
        _blogs = new ArrayList<>(max);
        _max = max;
    }

    protected RetrieveBlogs(String sql) {
        super(sql);
        _blogs = new ArrayList<>(1);
    }

    /**
     * Describe <code>populateStatement</code> method here.
     * 
     * @param preparedStatement a <code>PreparedStatement</code> value
     * @exception SQLException if an error occurs
     */
    @Override
    public void populateStatement(final PreparedStatement preparedStatement)
            throws SQLException {

        int index = 1;
        if (_memberId != -1) {
            preparedStatement.setInt(index++, _memberId);
        }
        preparedStatement.setInt(index++, _startBlog);
        preparedStatement.setInt(index, _max);
    }

    /**
     * Describe <code>processResults</code> method here.
     * 
     * @param resultSet a <code>ResultSet</code> value
     * @exception SQLException if an error occurs
     */
    @Override
    public final void processResults(final ResultSet resultSet)
            throws SQLException {
        if (resultSet != null) {
            while (resultSet.next()) {
                int index = 1;
                Blog blog = new Blog();
                blog.setId(resultSet.getInt(index++));
                blog.setSubject(resultSet.getString(index++));
                blog.setBody(resultSet.getString(index++));
                blog.setTimestamp(LoadHomeOperation.getDate(resultSet, index++));
                String displayName = resultSet.getString(index++);
                String firstName = resultSet.getString(index++);
                String temp = (displayName == null) ? firstName : displayName;
                blog.setName(temp);
                blog.setFrom(resultSet.getInt(index));
                _blogs.add(blog);
            }
        }
    }

    public final List<Blog> getBlogs() {
        return _blogs;
    }
}
