package magmac.api.head;

import magmac.api.None;
import magmac.api.iter.Iter;

import magmac.api.Option;
import java.util.function.Function;

public class FlatMapHead<T, R> implements Head<R> {
    private final Head<T> head;
    private final Function<T, Iter<R>> mapper;
    private Iter<R> current;

    public FlatMapHead(Head<T> head, Function<T, Iter<R>> mapper, Iter<R> initial) {
        this.mapper = mapper;
        this.head = head;
        this.current = initial;
    }

    @Override
    public Option<R> next() {
        while (true) {
            Option<R> maybeInner = this.current.next();
            if (maybeInner.isPresent()) {
                return maybeInner;
            }

            Option<T> maybeOuter = this.head.next();
            if (maybeOuter.isEmpty()) {
                return new None<>();
            }

            this.current = this.mapper.apply(maybeOuter.orElse(null));
        }
    }
}
