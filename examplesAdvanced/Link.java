import org.apache.tapestry5.annotations.Parameter;

public class Link extends AccessKey {
    @Parameter(required = true, defaultPrefix = "literal")
    private String _page;

    /**
     * Gets the value of page
     *
     * @return the value of page
     */
    public final String getPage() {
        return this._page;
    }

    /**
     * Sets the value of page
     *
     * @param argPage Value to assign to this.page
     */
    public final void setPage(final String argPage) {
        this._page = argPage;
    }
}