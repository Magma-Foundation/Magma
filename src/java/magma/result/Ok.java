package magma.result;

import magma.Tuple;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Ok<T, X>(T value) implements Result<T, X> {
    @Override
    public <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier) {
        return supplier.get().mapValue(otherValue -> new Tuple<>(value, otherValue));
    }

    @Override
    public <R> Result<R, X> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public Optional<T> findValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<X> findError() {
        return Optional.empty();
    }

    @Override
    public boolean isOk() {
        return true;
    }
}
