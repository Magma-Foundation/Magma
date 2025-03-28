package magma.result;

import magma.java.Options;
import magma.option.Option;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Ok<T, X>(T value) implements Result<T, X> {
    private Optional<T> findValue1() {
        return Optional.of(value);
    }

    private Optional<X> findError0() {
        return Optional.empty();
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
        return supplier.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
    }

    @Override
    public <R> Result<T, R> mapErr(Function<X, R> mapper) {
        return new Ok<>(value);
    }

    @Override
    public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
        return whenOk.apply(value);
    }

    @Override
    public Option<T> findValue() {
        return Options.wrap(findValue1());
    }

    @Override
    public Option<X> findError() {
        return Options.wrap(findError0());
    }
}
