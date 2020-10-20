package utils;

public class Validators {

    public static boolean portValidator(int port) {
        return port >= 0 && port < 49152;
    }
}
