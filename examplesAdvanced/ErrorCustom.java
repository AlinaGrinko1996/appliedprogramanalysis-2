
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Map;

public class ErrorCustom {
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String inputName;

    @Parameter(required = true)
    private Map<String, String> errors;

    @Inject
    private ComponentResources resources;

    public String getError() {
        String error = errors.get(inputName);
        String msg = null;

        if (!StringUtils.isEmpty(error)) {
            org.apache.tapestry5.ioc.Messages messages = resources.getMessages();
            if (messages.contains(error)) {
                msg = messages.get(error);
            } else {
                msg = error;
            }
        }
        return msg;
    }
}