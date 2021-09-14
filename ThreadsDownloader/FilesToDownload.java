import java.util.ArrayList;

public class FilesToDownload {
    private final ArrayList<FileUrl> urls;
    public boolean isFinished;

    public FilesToDownload(ArrayList<FileUrl> urls) {
        this.urls = urls;
    }

    public synchronized FileUrl startDownload() {
        FileUrl currentUrl = urls.get(0);
        urls.remove(0);
        if (urls.size() == 0) {
            isFinished = true;
        }
        return currentUrl;
    }
}
