package magma.api;

import magma.annotate.Actual;

public class Console {
    @Actual
    public static void printErrLn(String message) {
        System.err.println(message);
    }
}
