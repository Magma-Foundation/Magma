package magma.api;

import java.util.Optional;
import java.util.function.Function;

public record AbstractStream<T>(Head<T> provider) implements Stream<T> {
    @Override
    public Optional<T> head() {
        return this.provider.head();
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        var outer = this.provider;
        return new AbstractStream<>(() -> outer.head().map(mapper));
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
