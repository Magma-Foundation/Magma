package magma.api;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractStream<T> implements Stream<T> {
    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        return new AbstractStream<R>() {
            @Override
            public Optional<R> head() {
                return AbstractStream.this.head().map(mapper);
            }
        };
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        var current = collector.createInitial();

        while (true) {
            C finalCurrent = current;
            var tuple = Options.toTuple(head().map(head -> collector.fold(finalCurrent, head)), current);
            if (tuple.left()) {
                current = tuple.right();
            } else {
                return current;
            }
        }
    }
}
