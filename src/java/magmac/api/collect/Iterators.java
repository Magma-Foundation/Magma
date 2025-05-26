package magmac.api.collect;

import magmac.api.collect.head.RangeHead;
import magmac.api.collect.head.HeadedIter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Iterators {
    public static <T> Iter<T> fromSet(Set<T> set) {
        return fromList(new ArrayList<>(set));
    }

    private static <T> Iter<T> fromList(List<T> list) {
        return new HeadedIter<>(new RangeHead(list.size())).map(list::get);
    }
}
