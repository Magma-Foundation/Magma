package magmac.api.iter;

import magmac.api.Option;
import magmac.api.head.EmptyHead;
import magmac.api.head.HeadedIter;
import magmac.api.head.RangeHead;
import magmac.api.head.SingleHead;

/**
 * Factory methods for creating {@link Iter} instances.
 */

public final class Iters {
    private static <T> Iter<T> fromArray(T[] array) {
        return new HeadedIter<>(new RangeHead(array.length))
                .map((Integer index) -> array[index]);
    }

    /**
     * Creates an iterator from an {@link Option}, yielding zero or one element.
     */
    public static <T> Iter<T> fromOption(Option<T> option) {
        return option
                .<Iter<T>>map((T t) -> new HeadedIter<>(new SingleHead<>(t)))
                .orElseGet(() -> new HeadedIter<>(new EmptyHead<>()));
    }

    /**
     * Creates an iterator from the provided values.
     */
    public static <T> Iter<T> fromValues(T... values) {
        return Iters.fromArray(values);
    }

    /**
     * Returns an iterator with no elements.
     */
    public static <T> Iter<T> empty() {
        return new HeadedIter<>(new EmptyHead<>());
    }
}
