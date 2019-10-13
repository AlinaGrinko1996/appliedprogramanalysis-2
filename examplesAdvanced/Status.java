import java.util.Date;

public class Status {
    private Date _timestamp;
    private String _status;

    /**
     * Creates a new <code>Status</code> instance.
     */
    public Status() {
    }

    public final void setTimestamp(Date timestamp) {
        _timestamp = timestamp;
    }

    public final Date getTimestamp() {
        return _timestamp;
    }

    public final void setStatus(String status) {
        _status =  status;
    }

    public final String getStatus() {
        return _status;
    }
}
