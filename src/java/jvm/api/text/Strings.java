package jvm.api.text;

import magma.annotate.Actual;
import magma.annotate.Namespace;

@Namespace
public final class Strings {
    @Actual
    public static int length(final String stripped) {
        return stripped.length();
    }

    @Actual
    public static String sliceBetween(final String input, final int startInclusive, final int endExclusive) {
        return input.substring(startInclusive, endExclusive);
    }

    @Actual
    public static String sliceFrom(final String input, final int startInclusive) {
        return input.substring(startInclusive);
    }

    @Actual
    public static boolean isEmpty(final String cache) {
        return cache.isEmpty();
    }

    @Actual
    public static boolean equalsTo(final String left, final String right) {
        return left.equals(right);
    }

    @Actual
    public static String strip(final String input) {
        return input.strip();
    }

    @Actual
    public static boolean isBlank(final String value) {
        return value.isBlank();
    }

    @Actual
    public static char charAt(final String input, final int index) {
        return input.charAt(index);
    }
}
