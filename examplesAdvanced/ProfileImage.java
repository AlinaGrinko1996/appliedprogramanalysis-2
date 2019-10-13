
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SupportsInformalParameters
public class ProfileImage {

    private static final FileChecksumGenerator CHECKSUM_GENERATOR = FileChecksumGenerator.getInstance();

    @Parameter
    private boolean lazyLoad;

    @Parameter(required = true, defaultPrefix = "prop")
    private int _memberId;

    @Parameter(required = false, value = "false", defaultPrefix = "literal")
    private boolean _thumb;

    @Inject
    private ComponentResources _resources;

    @Inject
    private UserService userService;

    public org.apache.tapestry5.Link getProfilePageLink() throws ExecutionException {
        return userService.generatePageLink(_memberId, _thumb);
    }

    boolean beginRender(final MarkupWriter writer) throws IOException, ExecutionException {
        if (lazyLoad) {
            writer.element("img", "data-src", getProfilePageLink().toAbsoluteURI(), "class", "lazyload");
        } else {
            writer.element("img", "src", getProfilePageLink().toAbsoluteURI());

        }
        _resources.renderInformalParameters(writer);
        writer.end();
        return false;
    }
}