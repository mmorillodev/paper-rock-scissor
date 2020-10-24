package utils;

import static java.lang.System.err;
import static java.lang.System.out;

public class Console {


    public static void err(String msg) {
        err.println(msg);
    }

    public static void print(String msg){
        out.print(msg);
    }

    public static void println(Object msg){
        out.println(msg);
    }

    public static void br() {
        out.println();
    }

    public static void cls() {
        for (int i = 0; i < 100; i++) {
            out.println();
        }
    }
}
