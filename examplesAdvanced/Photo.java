
public class Photo extends MediaFile {
    /**
     * Creates a new <code>Photo</code> instance.
     */

    public Photo() {}

    public final String getDeleteFilename() {
        return getFilename().replace(".jpg", "");
    }

    @Override
    public final String getThumbnail() {
        return SystemUtils.getThumbnail(getFilename());
    }
}