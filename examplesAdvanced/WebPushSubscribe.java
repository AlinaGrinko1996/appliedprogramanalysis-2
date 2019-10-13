
import java.util.List;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


public class WebPushSubscribe {
    @Parameter
    @Property
    private boolean useCheckbox;
    @Parameter
    private boolean byDefault;
    @Inject
    private ComponentResources resources;
    @Inject
    private JavaScriptSupport javaScriptSupport;
    @Inject
    private Request request;

    public JSONObject getWebPushSpecs() {
        return new JSONObject()
                .put("senderId", getPushSenderId())
                .put("webPushURI", getWebPushURI())
                .put("unsubscribeURI", getUnsubscribeURI())
                .put("registeredTokens", getRegisteredTokens())
                .put("initByDefault", byDefault);
    }

    private String getWebPushURI() {
        return resources.createEventLink("setTokenId").toURI();
    }

    private String getUnsubscribeURI() {
        return resources.createEventLink("unsubscribe").toURI();
    }

    private String getPushSenderId() {
        return WebPushUtils.getFCMASenderId();
    }

    private JSONArray getRegisteredTokens() {
        List<WebPushParams> allRegisteredTokens =
                DatabaseUtils.getWebPushParams(getLoggedMemberId());
        JSONArray tokens = new JSONArray();
        for (WebPushParams registeredParams : allRegisteredTokens) {
            tokens.put(registeredParams.getTokenId());
        }
        return tokens;
    }

    @OnEvent("setTokenId")
    private void setTokenId() {
        String tokenId = request.getParameter("tokenId");
        String deviceType = request.getParameter("deviceType");
        String browserType = request.getParameter("browserType");
        setTokenId(tokenId, deviceType, browserType);
    }

    @OnEvent("unsubscribe")
    private void unsubscibe() {
        String tokenId = request.getParameter("tokenId");

        DatabaseUtils.unsubscribeWebPush(getLoggedMemberId(), tokenId);
    }

    public void setTokenId(String tokenId, String device, String browser) {
        String deviceType = (device == null) ? "" : device;
        String browserType = (browser == null) ? "" : browser;
        DatabaseUtils.insertWebPush(getLoggedMemberId(), tokenId, deviceType, browserType);
    }

    void setupRender() {
        javaScriptSupport.require("libs/firebase/firebase-subscribe").invoke("init").with(getWebPushSpecs());
    }
}