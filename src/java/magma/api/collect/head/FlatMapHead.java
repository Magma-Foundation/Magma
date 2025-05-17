package magma.api.collect.head;

import magma.api.collect.Query;
import magma.api.option.None;
import magma.api.option.Option;

import java.util.function.Function;

public final class FlatMapHead<T, R> implements Head<R> {
    private final Function<T, Query<R>> mapper;
    private final Head<T> head;
    private Query<R> current;

    public FlatMapHead(final Head<T> head, final Query<R> initial, final Function<T, Query<R>> mapper) {
        this.head = head;
        this.current = initial;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        while (true) {
            final var next = this.current.next();
            if (next.isPresent()) {
                return next;
            }

            final var tuple = this.head.next()
                    .map(this.mapper)
                    .toTuple(this.current);

            if (tuple.left()) {
                this.current = tuple.right();
            }
            else {
                return new None<R>();
            }
        }
    }
}
