package magma.api.collect;

import magma.api.collect.head.EmptyHead;
import magma.api.collect.head.Head;
import magma.api.collect.head.HeadedQuery;
import magma.api.collect.head.SingleHead;
import magma.api.option.Option;

public final class Queries {
    public static <T> Query<T> fromOption(final Option<T> option) {
        return new HeadedQuery<T>(option.map((T element) -> Queries.getTSingleHead(element)).orElseGet(() -> new EmptyHead<T>()));
    }

    private static <T> Head<T> getTSingleHead(final T element) {
        return new SingleHead<T>(element);
    }
}
