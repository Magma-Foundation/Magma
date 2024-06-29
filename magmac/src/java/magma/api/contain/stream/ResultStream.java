package magma.api.contain.stream;

import magma.api.Tuple;
import magma.api.contain.collect.Collector;
import magma.api.option.Option;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public record ResultStream<A, B>(Stream<Result<A, B>> stream) implements Stream<Result<A, B>> {
    private static <B, C> Stream<Result<C, B>> flatMapElement(Result<Stream<C>, B> headBResult) {
        return headBResult.match(
                onOk -> onOk.<Result<C, B>>map(Ok::new),
                err -> Streams.of(new Err<>(err))
        );
    }

    @Override
    public <R> Stream<R> map(Function<Result<A, B>, R> mapper) {
        return stream.map(mapper);
    }

    @Override
    public <C> C collect(Collector<Result<A, B>, C> collector) {
        return stream.collect(collector);
    }

    @Override
    public <C> C foldLeft(C current, BiFunction<C, Result<A, B>, C> folder) {
        return stream.foldLeft(current, folder);
    }

    @Override
    public Option<Result<A, B>> head() {
        return stream.head();
    }

    @Override
    public <R, E> Result<R, E> foldLeftToResult(R initial, BiFunction<R, Result<A, B>, Result<R, E>> mapper) {
        return stream.foldLeftToResult(initial, mapper);
    }

    @Override
    public boolean anyMatch(Predicate<Result<A, B>> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public Stream<Result<A, B>> filter(Predicate<Result<A, B>> filter) {
        return stream.filter(filter);
    }

    @Override
    public <R> Stream<R> flatMap(Function<Result<A, B>, Head<R>> mapper) {
        return stream.flatMap(mapper);
    }

    @Override
    public <R> Stream<Tuple<Result<A, B>, R>> extend(Function<Result<A, B>, R> mapper) {
        return stream.extend(mapper);
    }

    public <C> Stream<Result<C, B>> flatMapValue(Function<A, Stream<C>> mapper) {
        return map(inner -> inner.mapValue(mapper)).flatMap(ResultStream::flatMapElement);
    }
}
