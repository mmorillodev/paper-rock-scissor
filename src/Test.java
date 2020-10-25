import utils.Console;

public class Test {
    public static void main(String[] args) {
//        new ScannerUtils().getStringWithMessage("test message", "test error message", s -> s.equals("bypass"));
        String[] str = new String[5];
        Console.println(str.length);
        Console.println((int) (Math.random() * 3 + 1));
    }
}
