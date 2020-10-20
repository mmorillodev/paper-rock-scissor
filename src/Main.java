import utils.ConnectionUtils;
import exceptions.NoSuchOptionException;
import utils.ScannerUtils;
import utils.StaticResources;

public class Main {
    public static void main(String[] args) {
        int opt = new ScannerUtils().getIntWithMessage(StaticResources.MENU_OPTIONS, Main::optionValidator);

        connectOrCreateSession(opt);
    }

    private static void connectOrCreateSession(int opt) {
        try {
            new ConnectionUtils().connectOrCreateSession(opt);
        } catch (IllegalAccessException | InstantiationException | NoSuchOptionException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean optionValidator(int option) {
        return option == 1 || option == 2;
    }
}
