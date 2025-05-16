package magma.api.collect.head;

import magma.api.Tuple2;
import magma.api.collect.Query;
import magma.api.option.Option;

public record ZipHead<T, R>(Head<T> head, Query<R> other) implements Head<Tuple2<T, R>> {
    @Override
    public Option<Tuple2<T, R>> next() {
        return this.head.next().and(() -> this.other.next());
    }
}
