public class IpVerification {
    private int _memberId;
    private int _verificationCode;
    private String _ipAddress;
    private String _email;

    /**
     * Creates a new <code>IpVerification</code> instance.
     */
    public IpVerification() {
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
     * Gets the value of verificationCode
     *
     * @return the value of verificationCode
     */
    public final int getVerificationCode() {
        return this._verificationCode;
    }

    /**
     * Sets the value of verificationCode
     *
     * @param argVerificationCode Value to assign to this.verificationCode
     */
    public final void setVerificationCode(final int argVerificationCode) {
        this._verificationCode = argVerificationCode;
    }

    /**
     * Gets the value of ipAddress
     *
     * @return the value of ipAddress
     */
    public final String getIpAddress() {
        return this._ipAddress;
    }

    /**
     * Sets the value of ipAddress
     *
     * @param argIpAddress Value to assign to this.ipAddress
     */
    public final void setIpAddress(final String argIpAddress) {
        this._ipAddress = argIpAddress;
    }

    /**
     * Gets the value of email
     *
     * @return the value of email
     */
    public final String getEmail() {
        return this._email;
    }

    /**
     * Sets the value of email
     *
     * @param argEmail Value to assign to this.email
     */
    public final void setEmail(final String argEmail) {
        this._email = argEmail;
    }

}
