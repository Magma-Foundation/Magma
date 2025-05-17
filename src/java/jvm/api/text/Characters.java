package jvm.api.text;

import magma.annotate.Actual;
import magma.annotate.Namespace;

@Namespace
public final class Characters {
    @Actual
    public static boolean isDigit(final char c) {
        return Character.isDigit(c);
    }

    @Actual
    public static boolean isLetter(final char c) {
        return Character.isLetter(c);
    }
}
