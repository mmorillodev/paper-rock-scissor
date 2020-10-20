package utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class ScannerUtils {

    private Scanner scanner;

    public ScannerUtils() {
        this.scanner= new Scanner(System.in);
    }

    public int getIntWithMessage(String message, Predicate<Integer> validInput) {
        int input;
        boolean retry = false;

        do {
            input = 0;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextInt();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(!validInput.test(input) || retry);

        return input;
    }

    public int getIntWithMessage(String message) {
        int input;
        boolean retry = false;

        do {
            input = 0;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextInt();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(retry);

        return input;
    }

    public String getStringWithMessage(String message, Predicate<String> validInput) {
        String input;
        boolean retry = false;

        do {
            input = "";
            try {
                System.out.print(message);
                input = scanner.nextLine();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(!validInput.test(input) || retry);
        return input;
    }

    public String getStringWithMessage(String message) {
        String input;
        boolean retry = false;

        do {
            input = "";
            try {
                System.out.print(message);
                input = scanner.nextLine();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(retry);

        return input;
    }

    public String getString() {
        return this.getStringWithMessage("");
    }

    public double getDoubleWithMessage(String message, Predicate<Double> validInput) {
        double input;
        boolean retry = false;

        do {
            input = 0.0;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextDouble();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(!validInput.test(input) || retry);

        return input;
    }

    public double getDoubleWithMessage(String message) {
        double input;
        boolean retry = false;

        do {
            input = 0.0;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextDouble();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(retry);

        return input;
    }

    public double getFloatWithMessage(String message, Predicate<Float> validInput) {
        float input;
        boolean retry = false;

        do {
            input = 0.0f;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextFloat();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(!validInput.test(input) || retry);

        return input;
    }

    public double getFloatWithMessage(String message) {
        float input;
        boolean retry = false;

        do {
            input = 0.0f;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextFloat();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(retry);

        return input;
    }

    public double getByteWithMessage(String message, Predicate<Byte> validInput) {
        byte input;
        boolean retry = false;

        do {
            input = 0;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextByte();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(!validInput.test(input) || retry);

        return input;
    }

    public double getByteWithMessage(String message) {
        byte input;
        boolean retry = false;

        do {
            input = 0;
            if(retry) {
                scanner.nextLine();
                retry = false;
            }
            try {
                System.out.print(message);
                input = scanner.nextByte();
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(retry);

        return input;
    }

    public char getCharWithMessage(String message, Predicate<Character> validInput) {
        char input;
        boolean retry = false;

        do {
            input = 0;
            try {
                System.out.print(message);
                input = scanner.next().charAt(0);
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(!validInput.test(input) || retry);

        return input;
    }

    public char getCharWithMessage(String message) {
        char input;
        boolean retry = false;

        do {
            input = 0;
            try {
                System.out.print(message);
                input = scanner.next().charAt(0);
            } catch(InputMismatchException e) {
                retry = true;
            }
        } while(retry);

        return input;
    }

    public void clearBuffer() {
        scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
