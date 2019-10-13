public class Routing {

    private int _memberId;
    private String _processor;
    private String _timestamp;

    public Routing() {
    }

    public final void setMemberId(final int memberId) {
        this._memberId = memberId;
    }

    public final int getMemberId() {
        return this._memberId;
    }

    public final void setProcessor(final String processor) {
        this._processor = processor;
    }

    public final String getProcessor() {
        return this._processor;
    }

    public final void setTimestamp(final String timestamp) {
        this._timestamp = timestamp;
    }

    public final String getTimestamp() {
        return this._timestamp;
    }
}