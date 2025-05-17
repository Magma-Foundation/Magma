package jvm.api.io;

import magma.annotate.Actual;
import magma.annotate.Namespace;

@Namespace
public final class Console {
    @Actual
    public static void printErrLn(final String message) {
        System.err.println(message);
    }
}
