package jvm.api.io;

import magma.annotate.Actual;
import magma.annotate.Namespace;

@Namespace
public class Console {
    @Actual
    public static void printErrLn(String message) {
        System.err.println(message);
    }
}
