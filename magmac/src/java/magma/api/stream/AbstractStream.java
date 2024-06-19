package magma.api.stream;

import magma.api.option.Option;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.java.JavaOptionals;

import java.util.function.BiFunction;
import java.util.function.Function;

public record AbstractStream<T>(Head<T> provider) implements Stream<T> {
    @Override
    public <R, E> Result<R, E> foldRightToResult(R initial, BiFunction<R, T, Result<R, E>> mapper) {
        return this.fold(Ok.from(initial),
                (reResult, t) -> reResult.flatMapValue(
                        inner -> mapper.apply(inner, t)));
    }

    @Override
    public <R> Stream<R> map(Function<T, R> mapper) {
        var outer = this.provider;
        return new AbstractStream<>(() -> outer.head().map(mapper));
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        var current = collector.createInitial();
        return fold(current, collector::fold);
    }

    private <C> C fold(C current, BiFunction<C, T, C> folder) {
        while (true) {
            C finalCurrent = current;
            var tuple = JavaOptionals.toTuple(JavaOptionals.toNative(head()).map(head -> folder.apply(finalCurrent, head)), current);
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
