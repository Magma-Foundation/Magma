package magmac.api.iter;

import magmac.api.Tuple2;
import magmac.api.head.HeadedIter;
import magmac.api.head.RangeHead;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Iters {
    public static <T> Iter<T> fromList(List<T> list) {
        return new HeadedIter<>(new RangeHead(list.size())).map(list::get);
    }

    public static <K, V> Iter<Tuple2<K, V>> fromMap(Map<K, V> map) {
        return Iters.fromList(new ArrayList<>(map.entrySet()))
                .map(entry -> new Tuple2<>(entry.getKey(), entry.getValue()));
    }
}
