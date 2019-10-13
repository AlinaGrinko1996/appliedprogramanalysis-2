import org.apache.tapestry5.ioc.annotations.Inject;

import javax.servlet.http.HttpServletRequest;

public class HomeHeader {

    @Inject
    private UserService userService;
    @Inject
    private HttpServletRequest request;

    /**
     * Creates a new <code>HomeHeader</code> instance.
     */
    public HomeHeader() {
    }

    Object onActionFromLogout() {
        return userService.logout();
    }

    public boolean isMobile() {
        return isMobile();
    }

    public String getSnapsURL() {
      return SystemUtils.getSnapsURL(request);
    }
}