package magma.api.stream;

import magma.api.option.Option;
import magma.java.JavaOptionals;

import java.util.Optional;
import java.util.function.Function;

public record AbstractStream<T>(Head<T> provider) implements Stream<T> {

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
            var tuple = JavaOptionals.toTuple(JavaOptionals.toNative(head()).map(head -> collector.fold(finalCurrent, head)), current);
            if (tuple.left()) {
                current = tuple.right();
            } else {
                return current;
            }
        }
    }

    @Override
    public Option<T> head() {
        return provider.head();
    }
}
