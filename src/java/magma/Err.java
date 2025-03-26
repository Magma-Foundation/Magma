package magma;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Err<T, X>(X error) implements Result<T, X> {
    @Override
    public Optional<T> findValue() {
        return Optional.empty();
    }

    @Override
    public Optional<X> findError() {
        return Optional.of(error);
    }

    @Override
    public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other) {
        return new Err<>(error);
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Err<>(error);
    }
}
