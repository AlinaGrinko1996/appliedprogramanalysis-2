
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.List;

@Import(module = {"inner/offer", "inner/moments-timestamp"})
public class Offer {
    private static final Logger LOG = LogUtils.getLogger(Offer.class);
    private static final int MAX_NUMBER_OF_ENTRIES = 30;
    public static final int SYSTEM_ADMIN = 0;

    @Parameter(defaultPrefix = "literal")
    private boolean _allBlogs = true;

    @Parameter()
    private int _memberId;

    @Parameter
    private int _blogId = -1;

    public static final int MAX_CHARACTERS = 100000;

    @Persist
    private Profile _profile;

    private List<Blog> _blogs;

    private Blog _blog;

    @Parameter
    @Property
    private boolean reset;

    @Persist
    @Property
    private int totalEntries;

    @InjectComponent
    private PagingAjax paging;

    @InjectPage
    private Flag _flagBlog;

    @Inject
    private JavaScriptSupport javaScriptSupport;


    public final int getMemberId() {
        return _memberId;
    }

    public final void setMemberId(int memberId) {
        _memberId = memberId;
    }

    public final boolean isAllBlogs() {
        return _allBlogs;
    }

    public final void setAllBlogs(boolean allBlogs) {
        _allBlogs = allBlogs;
    }

    public final List<Blog> getBlogs() {
        return _blogs;
    }

    public final void setBlogs(List<Blog> blogs) {
        _blogs = blogs;
    }

    /**
     * Gets the value of blog
     *
     * @return the value of blog
     */
    public final Blog getBlog() {
        return this._blog;
    }

    /**
     * Sets the value of blog
     *
     * @param argBlog Value to assign to this.blog
     */
    public final void setBlog(final Blog argBlog) {
        this._blog = argBlog;
    }

    /**
     * Gets the value of blogId
     *
     * @return the value of blogId
     */
    public final int getBlogId() {
        return this._blogId;
    }

    /**
     * Sets the value of blogId
     *
     * @param argBlogId Value to assign to this.blogId
     */
    public final void setBlogId(final int argBlogId) {
        this._blogId = argBlogId;
    }

    public String getSummary() {
        Blog blog = getBlog();
        String body = blog.getBody();
        if (isNeedsMore()) {
            return body.substring(0, MAX_CHARACTERS);
        } else {
            return body;
        }
    }

    public boolean isNeedsMore() {
        Blog blog = getBlog();
        String body = blog.getBody();
        return (getBlogId() < 0) && (body.length() > MAX_CHARACTERS);
    }

    public String getMore() {
        Blog blog = getBlog();
        String body = blog.getBody();
        return body.substring(MAX_CHARACTERS + 1);
    }

    /**
     * Gets the value of profile
     *
     * @return the value of profile
     */
    public final Profile getProfile() {
        return this._profile;
    }

}