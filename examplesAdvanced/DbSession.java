import java.util.Date;

public class DbSession {
    private int _memberId;
    private String _sessionId;
    private Date _timestamp;
    private boolean _isMobile;

    /**
     * Creates a new <code>DbSession</code> instance.
     */
    public DbSession(int memberId,
                     String sessionId,
                     Date timestamp,
                     boolean isMobile) {
        _memberId = memberId;
        _sessionId = sessionId;
        _timestamp = timestamp;
        _isMobile = isMobile;
    }

    /**
     * Gets the value of memberId
     *
     * @return the value of memberId
     */
    public final int getMemberId() {
        return this._memberId;
    }

    /**
     * Sets the value of memberId
     *
     * @param argMemberId Value to assign to this.memberId
     */
    public final void setMemberId(final int argMemberId) {
        this._memberId = argMemberId;
    }

    /**
     * Gets the value of sessionId
     *
     * @return the value of sessionId
     */
    public final String getSessionId() {
        return this._sessionId;
    }

    /**
     * Sets the value of sessionId
     *
     * @param argSessionId Value to assign to this.sessionId
     */
    public final void setSessionId(final String argSessionId) {
        this._sessionId = argSessionId;
    }

    /**
     * Gets the value of timestamp
     *
     * @return the value of timestamp
     */
    public final Date getTimestamp() {
        return this._timestamp;
    }

    /**
     * Sets the value of timestamp
     *
     * @param argTimestamp Value to assign to this.timestamp
     */
    public final void setTimestamp(final Date argTimestamp) {
        this._timestamp = argTimestamp;
    }
    
    /**
     * Gets the value of isMobile
     *
     * @return the value of isMobile
     */
    public boolean isMobile() {
        return this._isMobile;
    }
    
    /**
     * Sets the value of isMobile
     *
     * @param mobile Value to assign to this.isMobile
     */
    public void setMobile(boolean isMobile) {
        this._isMobile = isMobile;
    }
}
