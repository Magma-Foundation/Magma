package magma.api.result;

import magma.api.option.Option;
import magma.java.JavaOptionals;

import java.util.Optional;
import java.util.function.Function;

public record Err<T, E>(E value) implements Result<T, E> {
    private Optional<T> findValue1() {
        return Optional.empty();
    }

    private Optional<E> findErr0() {
        return Optional.of(value);
    }

    @Override
    public <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper) {
        return new Err<>(value);
    }

    @Override
    public <R> Result<R, E> mapValue(Function<T, R> mapper) {
        return new Err<>(value);
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public <R> Result<T, R> mapErr(Function<E, R> mapper) {
        return new Err<>(mapper.apply(value));
    }

    @Override
    public <R> R match(Function<T, R> onOk, Function<E, R> onErr) {
        return onErr.apply(value);
    }

    @Override
    public Option<T> findValue() {
        return JavaOptionals.fromNative(findValue1());
    }

    @Override
    public Option<E> findErr() {
        return JavaOptionals.fromNative(findErr0());
    }
}
