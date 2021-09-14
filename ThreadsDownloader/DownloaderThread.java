import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloaderThread extends Thread {
    private int threadIndex;
    private FilesToDownload urls;

    public DownloaderThread (int threadIndex, FilesToDownload urls) {
        this.threadIndex = threadIndex;
        this.urls = urls;
    }

    public void run() {
        while (!urls.isFinished) {
            FileUrl fileUrl = urls.startDownload();
            try {
                System.setProperty("http.agent", "Chrome");
                URL url = new URL(fileUrl.getUrl());
                System.out.println("Thread-" + threadIndex + " start download file number " + fileUrl.getIndex());

                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream(fileUrl.getFileName());
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                System.out.println("Thread-" + threadIndex + " finish download file number " + fileUrl.getIndex());
            } catch (Exception e) {
                System.out.println("Downloading error: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
