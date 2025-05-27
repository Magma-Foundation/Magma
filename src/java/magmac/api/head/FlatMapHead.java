package magmac.api.head;

import magmac.api.iter.Iter;

import java.util.Optional;
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
    public Optional<R> next() {
        while (true) {
            // 1) Try to pull from the current inner
            Optional<R> maybeInner = this.current.next();
            if (maybeInner.isPresent()) {
                return maybeInner;
            }

            // 2) Current inner is exhausted, pull the next T
            Optional<T> maybeOuter = this.head.next();
            if (maybeOuter.isEmpty()) {
                // No more Ts ⇒ done
                return Optional.empty();
            }

            // 3) Map T to Iter<R> and switch to its head
            this.current = this.mapper.apply(maybeOuter.get());
        }
    }
}
