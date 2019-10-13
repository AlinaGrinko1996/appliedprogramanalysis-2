import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


@Import(stylesheet = {"context:/scripts/chat/emoji/css/nanoscroller.css"})
public class TinyMCE {
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Parameter(required = true, defaultPrefix = "literal")
    private String _css;

    @Parameter(value = "true", defaultPrefix = "literal")
    private boolean _media;

    @Parameter(defaultPrefix = "literal")
    private String _height = "150";

    @Parameter(defaultPrefix = "literal")
    private int _maxLength = 65000;

    public void setupRender() {
        if (isMale()) {
            if (isAdmin()) {
                javaScriptSupport.require("inner/tinyMCEInit").invoke("initTinyMCE")
                        .with(_height, _maxLength, _css, true, true);
            } else {
                javaScriptSupport.require("inner/tinyMCEInit").invoke("initTinyMCE")
                        .with(_height, _maxLength, _css, false, false);
            }
        } else {
            javaScriptSupport.require("inner/tinyMCEInit").invoke("initTinyMCE")
                    .with(_height, _maxLength, _css, false, _media);
        }
    }
}