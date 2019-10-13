import java.io.Serializable;

public class MediaFile implements Serializable {

    private static final long serialVersionUID = 8698805599852221035L;

    private int memberId;
    private String filename;
    private String comment;
    private String timestamp;


    public String getThumbnail() {
        return filename;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String fileName) {
        this.filename = fileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
