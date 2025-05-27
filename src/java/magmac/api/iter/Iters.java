package magmac.api.iter;

import magmac.api.Option;
import magmac.api.head.EmptyHead;
import magmac.api.head.HeadedIter;
import magmac.api.head.RangeHead;
import magmac.api.head.SingleHead;

public final class Iters {
    public static <T> Iter<T> fromArray(T[] array) {
        return new HeadedIter<>(new RangeHead(array.length)).map(index -> array[index]);
    }

    public static <T> Iter<T> fromOption(Option<T> option) {
        return option
                .<Iter<T>>map(t -> new HeadedIter<>(new SingleHead<>(t)))
                .orElseGet(() -> new HeadedIter<>(new EmptyHead<>()));
    }

    public static <T> Iter<T> fromValues(T... values) {
        return Iters.fromArray(values);
    }

    public static <T> Iter<T> empty() {
        return new HeadedIter<>(new EmptyHead<>());
    }
}
