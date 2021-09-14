public class Program {

    public static void main(String[] args) {
        boolean isDevMode = args.length > 0 && args[0].equals("--profile=dev");
        Menu menu = new Menu(isDevMode);
    }
}
