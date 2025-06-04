package magmac.api.collect.list;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Factory and helper methods for working with {@link List} values.
 */

public final class Lists {
    /**
     * Creates a list containing the given elements.
     */
    public static <T> List<T> of(T... elements) {
        return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
    }

    /**
     * Returns an empty list.
     */
    public static <T> List<T> empty() {
        return new JVMList<>();
    }

    /**
     * Creates a list repeating {@code element} {@code size} times.
     */
    public static <T> List<T> repeat(T element, int size) {
        List<T> copy = Lists.empty();
        var i = 0;
        while (i < size) {
            copy = copy.addLast(element);
            i++;
        }
        return copy;
    }
}
