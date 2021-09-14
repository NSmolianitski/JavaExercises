import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments.");
            return;
        } else if (!args[0].startsWith("--threadsCount=")) {
            System.out.println("Wrong arguments.");
            return;
        }

        try {
            int threadsCount = Integer.parseInt(args[0].substring(15));
            List<String> urlsList = Files.readAllLines(Paths.get("files_urls.txt"));
            ArrayList<FileUrl> urls = new ArrayList<>(urlsList.size());
            for (String urlString : urlsList) {
                urls.add(new FileUrl(urlString));
            }

            FilesToDownload filesToDownload = new FilesToDownload(urls);
            List<DownloaderThread> threads = new LinkedList<>();
            for (int i = 0; i < threadsCount; ++i) {
                threads.add(new DownloaderThread(i + 1, filesToDownload));
            }

            for (DownloaderThread thread : threads) {
                thread.start();
            }

            for (DownloaderThread thread : threads) {
                thread.join();
            }
        } catch (NumberFormatException e) {
            System.out.println("Argument is not a number.");
            return;
        } catch (IOException e) {
            System.out.println("File reading error.");
            return;
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
            return;
        }
    }
}