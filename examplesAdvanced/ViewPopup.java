import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.Date;

public class ViewPopup {

    @InjectComponent("viewZone")
    private Zone zone;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    public void updateZone() {
        ajaxResponseRenderer
                .addRender(zone)
                .addCallback(new JavaScriptCallback() {
            @Override
            public void run(JavaScriptSupport javascriptSupport) {
                javascriptSupport.require("inner/view_popup").invoke("showPopup");
            }
        });
    }

    public String getFormattedDate(final Date date) {
        final String timeZoneId = getProfile().getTimeZone().getId();
        return TimeUtils.getFormattedDate(date, timeZoneId);
    }
}