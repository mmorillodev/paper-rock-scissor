import utils.ConnectionUtils;
import utils.ScannerUtils;
import utils.StaticResources;

public class Main {
    public static void main(String[] args) {
        char opt = new ScannerUtils().getChar(
                StaticResources.MENU_OPTIONS,
                c -> Character.toLowerCase(c) == 's' || Character.toLowerCase(c) == 'c'
        );

        try {
            new ConnectionUtils().connect(opt);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
