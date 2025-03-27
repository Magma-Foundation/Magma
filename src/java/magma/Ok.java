package magma;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Ok<T, X>(T value) implements Result<T, X> {
    @Override
    public Optional<T> findValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<X> findError() {
        return Optional.empty();
    }

    @Override
    public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
        return other.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public <R> Result<T, R> mapErr(Function<X, R> mapper) {
        return new Ok<>(value);
    }

    @Override
    public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
        return whenOk.apply(value);
    }
}
