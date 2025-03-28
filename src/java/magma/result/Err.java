package magma.result;

import magma.option.JavaOptions;
import magma.option.Option;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Err<T, X>(X error) implements Result<T, X> {
    private Optional<T> findValue1() {
        return Optional.empty();
    }

    private Optional<X> findError0() {
        return Optional.of(error);
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Err<>(error);
    }

    @Override
    public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
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
    public Option<T> findValue() {
        return JavaOptions.wrap(findValue1());
    }

    @Override
    public Option<X> findError() {
        return JavaOptions.wrap(findError0());
    }
}
