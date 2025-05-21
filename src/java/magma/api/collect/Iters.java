package magma.api.collect;

import magma.api.collect.head.EmptyHead;
import magma.api.collect.head.Head;
import magma.api.collect.head.HeadedIter;
import magma.api.collect.head.RangeHead;
import magma.api.collect.head.SingleHead;
import magma.api.option.Option;

public final class Iters {
    public static <T> Iter<T> fromOption(Option<T> option) {
        return new HeadedIter<T>(option.map((T element) -> Iters.getTSingleHead(element)).orElseGet(() -> new EmptyHead<T>()));
    }

    private static <T> Head<T> getTSingleHead(T element) {
        return new SingleHead<T>(element);
    }

    public static <T> Iter<T> fromArray(T[] array) {
        return new HeadedIter<>(new RangeHead(array.length)).map((Integer index) -> array[index]);
    }
}
