package magma.api.text;

import magma.annotate.Actual;
import magma.annotate.Namespace;

@Namespace
public class Characters {
    @Actual
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    @Actual
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }
}
