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
            // 1) Try to pull from the current inner
            Option<R> maybeInner = this.current.next();
            if (maybeInner.isPresent()) {
                return maybeInner;
            }

            // 2) Current inner is exhausted, pull the next T
            Option<T> maybeOuter = this.head.next();
            if (maybeOuter.isEmpty()) {
                // No more Ts ⇒ done
                return new None<>();
            }

            // 3) Map T to Iter<R> and switch to its head
            this.current = this.mapper.apply(maybeOuter.orElse(null));
        }
    }
}
