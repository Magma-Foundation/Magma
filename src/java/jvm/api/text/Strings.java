package jvm.api.text;

import magma.annotate.Actual;
import magma.annotate.Namespace;

@Namespace
public final class Strings {
    @Actual
    public static int length(String stripped) {
        return stripped.length();
    }

    @Actual
    public static String sliceBetween(String input, int startInclusive, int endExclusive) {
        return input.substring(startInclusive, endExclusive);
    }

    @Actual
    public static String sliceFrom(String input, int startInclusive) {
        return input.substring(startInclusive);
    }

    @Actual
    public static boolean isEmpty(String cache) {
        return cache.isEmpty();
    }

    @Actual
    public static boolean equalsTo(String left, String right) {
        return left.equals(right);
    }

    @Actual
    public static String strip(String input) {
        return input.strip();
    }

    @Actual
    public static boolean isBlank(String value) {
        return value.isBlank();
    }

    @Actual
    public static char charAt(String input, int index) {
        return input.charAt(index);
    }
}
