package utils;

public class Validators {

    public static boolean portValidator(int port) {
        return port >= 0 && port < 49152;
    }

    public static boolean optionValidator(int option) {
        return option == 1 || option == 2;
    }

    public static boolean bypass(Object input) {
        return true;
    }
}
