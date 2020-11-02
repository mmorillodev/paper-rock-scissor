import utils.ConnectionUtils;
import exceptions.NoSuchOptionException;
import utils.ScannerUtils;
import resources.Strings;
import resources.Validators;

public class Main {
    public static void main(String[] args) {
        int opt = new ScannerUtils().getIntWithMessage(Strings.MENU_OPTIONS, Validators::optionValidator);

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
}
