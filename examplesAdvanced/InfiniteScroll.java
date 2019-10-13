import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.ArrayList;
import java.util.List;

import static org.apache.tapestry5.BindingConstants.PROP;

public class InfiniteScroll implements ClientElement {

    @Parameter
    @Property
    private Object row;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String event;

    /**
     * zone id which will be used to upload new page elements. should be in list after pageScroll component
     */
    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String zone;

    /**
     * id of scrollable element
     */
    @Parameter(allowNull = false, value = "false", defaultPrefix = BindingConstants.LITERAL)
    private boolean useGlobalScroll;

    /**
     * id of scrollable element. Don't uses if useGlobalScroll == true
     */
    @Parameter(allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String scroller;

    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Parameter(required = false, value = "false", defaultPrefix = BindingConstants.LITERAL)
    private boolean useViewMoreButton;

    @Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
    private boolean enableScroll;
    /**
     * property that is used to reset start index (set to 0)
     */
    @Parameter(required = false, defaultPrefix = PROP)
    @Property
    private boolean reset;

    private String assignedClientId;

    @Property
    @Parameter(required = true, allowNull = false, defaultPrefix = PROP)
    private Integer maxEntries;

    @Parameter
    private JavaScriptCallback javaScriptCallback;

    @Persist
    private int delta;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private Block nextPageBlock;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    private int startIndex;
    private boolean retrieveAll;

    void setupRender() {
        if (reset) {
            startIndex = 0;
            reset = false;
        }
        delta = 0;
    }

    @BeginRender
    void assignClientId() {
        assignedClientId = javaScriptSupport.allocateClientId(clientId);
    }

    @AfterRender
    void addJavaScript() {
        JSONObject specs = new JSONObject()
                .put("isGlobalScroll", useGlobalScroll)
                .put("scroller", scroller)
                .put("scrollURI", getScrollURI())
                .put("maxEntries", maxEntries)
                .put("enableScroll", enableScroll)
                .put("useButton", useViewMoreButton)
                .put("zoneId", zone);
        javaScriptSupport.require("home/page-scroll").invoke("initScroller").with(specs);
    }

    @OnEvent("scroll")
    Object scroll(int pageArg, boolean retrieveAll) {
        this.startIndex = pageArg * maxEntries - delta;
        this.retrieveAll = retrieveAll;

        if (javaScriptCallback != null) {
            ajaxResponseRenderer.addCallback(javaScriptCallback);
        }
        return nextPageBlock;
    }

    public List<?> getNextPage() {
        CaptureResultCallback<PageSource> resultCallback = new CaptureResultCallback<>();
        resources.triggerEvent(event, new Object[]{startIndex, retrieveAll}, resultCallback);

        PageSource pageSource = resultCallback.getResult();

        List<?> result = pageSource.getSource();
        result = (result == null) ? new ArrayList<>() : result;
        return result;
    }

    @Override
    public String getClientId() {
        return assignedClientId;
    }

    public String getScrollURI() {
        return resources.createEventLink("scroll", "pageScrollIndex", "retrieveAll").toURI();
    }

    public int getCurrentPage() {
        return (startIndex / maxEntries) + 1;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        if (reset) {
            startIndex = 0;
            reset = false;
            delta = 0;
        }
        return startIndex;
    }

    public void incrementIndex() {
        delta += 1;
    }
}