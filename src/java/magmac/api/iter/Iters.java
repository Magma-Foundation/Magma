package magmac.api.iter;

import magmac.api.Tuple2;
import magmac.api.head.EmptyHead;
import magmac.api.head.HeadedIter;
import magmac.api.head.RangeHead;
import magmac.api.head.SingleHead;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Iters {
    public static <T> Iter<T> fromList(List<T> list) {
        return new HeadedIter<>(new RangeHead(list.size())).map(list::get);
    }

    public static <K, V> Iter<Tuple2<K, V>> fromMap(Map<K, V> map) {
        return Iters.fromList(new ArrayList<>(map.entrySet()))
                .map(entry -> new Tuple2<>(entry.getKey(), entry.getValue()));
    }

    public static <T> Iter<T> fromArray(T[] array) {
        return new HeadedIter<>(new RangeHead(array.length)).map(index -> array[index]);
    }

    public static <T> Iter<T> fromOption(Optional<T> optional) {
        return optional
                .<Iter<T>>map(t -> new HeadedIter<>(new SingleHead<>(t)))
                .orElseGet(() -> new HeadedIter<>(new EmptyHead<>()));
    }

    public static <T> Iter<T> fromValues(T... values) {
        return Iters.fromArray(values);
    }
}
