public class FileUrl {
    final private String index;
    final private String url;
    final private String fileName;

    public FileUrl(String urlString) {
        String[] splittedString = urlString.split(" +");
        index = splittedString[0];
        url = splittedString[1];
        String[] splittedUrl = splittedString[1].split("/");
        fileName = splittedUrl[splittedUrl.length - 1];
    }

    public String getIndex() {
        return index;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }
}
