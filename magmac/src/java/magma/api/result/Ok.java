package magma.api.result;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.function.Function;

public record Ok<T, E>(T value) implements Result<T, E> {
    public static <E, R> Result<R, E> from(R value) {
        return new Ok<>(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return mapper.apply(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Ok<>(mapper.apply(value));
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public <R> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Ok<>(value);
    }

    @Override
    public <R> R match(Function<T, R> onOk, Function<E, R> onErr) {
        return onOk.apply(value);
    }

    @Override
    public Option<T> findValue() {
        return new Some<>(value);
    }

    @Override
    public Option<E> findErr() {
        return None.None();
    }
}
