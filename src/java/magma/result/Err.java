package magma.result;

import magma.Tuple;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public void consume(Consumer<T> whenOk, Consumer<X> whenErr) {
        whenErr.accept(error);
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Err<>(error);
    }

    @Override
    public <R> Result<T, R> mapErr(Function<X, R> mapper) {
        return new Err<>(mapper.apply(error));
    }

    @Override
    public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
        return new Err<>(error);
    }

    @Override
    public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
        return whenErr.apply(error);
    }

    @Override
    public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
        return new Err<>(error);
    }

    @Override
    public boolean isOk() {
        return false;
    }
}
