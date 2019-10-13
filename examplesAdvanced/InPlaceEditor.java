import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.TextStreamResponse;

@Import(library = {"${tapestry.scriptaculous}/prototype.js",
        "${tapestry.scriptaculous}/effects.js",
        "${tapestry.scriptaculous}/controls.js"})
public class InPlaceEditor {
    public static final Logger LOG = LogUtils.getLogger(InPlaceEditor.class);
    public static final String EVENT_NAME = "edit";
    private static final String PARAM_NAME = "t:InPlaceEditor";
    private static final String OK_BUTTON_NAME = "Save";

    @Parameter(required = true)
    private String value;

    @Parameter(required = true)
    private String filename;

    @Parameter(required = true)
    private String editing;

    @Parameter
    private boolean triggerLeaveEdit;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;


    @InjectPage
    private MyVideos _myVideos;

    void afterRender(MarkupWriter writer) {
        String elementName = resources.getElementName();
        if (elementName == null) {
            elementName = "span";
        }

        String clientId = javaScriptSupport.allocateClientId(resources.getId());
        writer.element(elementName, "id", clientId);

        resources.renderInformalParameters(writer);
        if (value != null) {
            writer.write(value);
        }
        writer.attributes("onClick", "setCookie('value','" + value + "'); setCookie('filename','"
                + filename + "');");
        writer.attributes("class", "renamevideo");
        //LOG.info( clientId + " "+value);
        writer.end();

        JSONObject config = new JSONObject();
        config.put("paramName", PARAM_NAME);
        config.put("okText", OK_BUTTON_NAME);

        Link link = resources.createEventLink(EVENT_NAME);
        javaScriptSupport.require("/scripts/modules/InPlaceEditor.js")
                .with(clientId, link.toAbsoluteURI(), config, triggerLeaveEdit);
    }

    Object onEdit() {
        value = request.getParameter(PARAM_NAME);
        //String name = "* ";
        //LOG.info(name + " **** " + value);
        //LOG.info(">>>>:" + _myVideos.getEdit());
        return new TextStreamResponse("text/plain", editing);
    }
}