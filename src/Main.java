import utils.ConnectionUtils;
import utils.ScannerUtils;
import resources.Validators;
import exceptions.NoSuchOptionException;

public class Main {
    public static void main(String[] args) {
        int opt = new ScannerUtils().getIntWithMessage("[1] Server\n[2] Client\n> ", Validators::optionValidator);

        connectOrCreateSession(opt);
    }

    private static void connectOrCreateSession(int opt) {
        try {
            new ConnectionUtils().connectOrCreateSession(opt);
        } catch (IllegalAccessException | InstantiationException | NoSuchOptionException e) {
            System.err.println("Unexpected error thrown: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
