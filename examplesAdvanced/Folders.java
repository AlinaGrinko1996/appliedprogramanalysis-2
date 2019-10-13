import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import java.util.List;

public class Folders {
    private static Logger LOG = LogUtils.getLogger(Folders.class);
    private int MAX_PHOTOS_PER_ROW = 6;
    @Property
    private int index;
    @Property
    private Folder folder;
    @Property
    private List<Folder> folders;
    @Inject
    private ComponentResources resources;
    @Inject
    private Request request;
    @Inject
    private FolderService folderService;

    public boolean getOpenRow() {
        return (index % MAX_PHOTOS_PER_ROW) == 0;
    }

    public boolean getCloseRow() {
        return (index % MAX_PHOTOS_PER_ROW) == (MAX_PHOTOS_PER_ROW - 1);
    }

    public boolean isHasFolders() {
        return (folders != null) && (folders.size() > 0);
    }

    public boolean isShowDeleting() {
        return !folderService.isSnapsFolder(folder.getFolder());
    }

    public String getFolderClass() {
        return folderService.isSnapsFolder(folder.getFolder()) ? "snaps-folder" : "";
    }

    Object onActionFromFolder(String foldername) {
        if (LOG.isInfo()) {
            LOG.info("------> " + foldername);
        }
        return _photos;
    }

    private void triggerPjax() {
        if (request.isXHR()) {
            resources.triggerEvent("pjax", null, null);
        }
    }
}
