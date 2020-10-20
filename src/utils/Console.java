package utils;

import static java.lang.System.out;

public class Console {

    public static void print(String msg){
        out.print(msg);
    }

    public static void println(String msg){
        out.println(msg);
    }

    public static void cls() {
        for (int i = 0; i < 100; i++) {
            out.println();
        }
    }
}