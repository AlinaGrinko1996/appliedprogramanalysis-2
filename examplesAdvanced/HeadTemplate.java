import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(module = {"general/new"})
public class HeadTemplate {
    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
    private String title;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
    private String keywords;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
    private String description;

    @Inject
    private JavaScriptSupport javaScriptSupport;
    @Inject
    private Request request;

    protected void setupRender() {
        if (!request.isXHR()) {
            javaScriptSupport.require("trackJsMetadata");
        }
    }
}