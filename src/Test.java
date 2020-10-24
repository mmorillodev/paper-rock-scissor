import utils.ScannerUtils;
import utils.Validators;

public class Test {
    public static void main(String[] args) {
        new ScannerUtils().getStringWithMessage("test message", "test error message", s -> s.equals("bypass"));
    }
}
