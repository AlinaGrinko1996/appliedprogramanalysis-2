
import org.apache.tapestry5.annotations.Parameter;

public class AccessKey {
    @Parameter(required = true, defaultPrefix = "literal")
    private String _value;
    @Parameter(required = true, defaultPrefix = "literal")
    private String _accessKey;

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public final String getValue() {
        return this._value;
    }

    /**
     * Sets the value of value
     *
     * @param argValue Value to assign to this.value
     */
    public final void setValue(final String argValue) {
        this._value = argValue;
    }

    /**
     * Gets the value of accessKey
     *
     * @return the value of accessKey
     */
    public final String getAccessKey() {
        return this._accessKey;
    }

    /**
     * Sets the value of accessKey
     *
     * @param argAccessKey Value to assign to this.accessKey
     */
    public final void setAccessKey(final String argAccessKey) {
        this._accessKey = argAccessKey;
    }

    public String getLink() {
        StringBuffer link = new StringBuffer();
        char[] chars = getValue().toCharArray();
        boolean found = false;
        char accessKey = getAccessKey().charAt(0);
        for (char c : chars) {
            if (!found && (c == accessKey)) {
                link.append("<u>").append(c).append("</u>");
                found = true;
            } else {
                link.append(c);
            }
        }
        return link.toString();
    }


}
