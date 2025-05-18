package magma.api.collect;

import magma.api.collect.head.EmptyHead;
import magma.api.collect.head.Head;
import magma.api.collect.head.HeadedQuery;
import magma.api.collect.head.SingleHead;
import magma.api.option.Option;
import magma.app.Main;

public final class Iterators {
    public static <T> Query<T> fromOption(Option<T> option) {
        return new HeadedQuery<T>(option.map((T element) -> Iterators.getTSingleHead(element)).orElseGet(() -> new EmptyHead<T>()));
    }

    private static <T> Head<T> getTSingleHead(T element) {
        return new SingleHead<T>(element);
    }
}
