package magma.api.collect.head;

import magma.api.collect.Iter;
import magma.api.option.None;
import magma.api.option.Option;

import java.util.function.Function;

public final class FlatMapHead<T, R> implements Head<R> {
    private final Function<T, Iter<R>> mapper;
    private final Head<T> head;
    private Iter<R> current;

    public FlatMapHead(Head<T> head, Iter<R> initial, Function<T, Iter<R>> mapper) {
        this.head = head;
        this.current = initial;
        this.mapper = mapper;
    }

    @Override
    public Option<R> next() {
        while (true) {
            var next = this.current.next();
            if (next.isPresent()) {
                return next;
            }

            var tuple = this.head.next()
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
