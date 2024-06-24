package magma.api.result;

import magma.api.option.Option;

import java.util.function.Function;

public interface Result<T, E> {
    Option<T> findValue();

    Option<E> findErr();

    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    boolean isOk();

    <R> Result<T, R> mapErr(Function<E, R> mapper);

    <R> R match(Function<T, R> onOk, Function<E, R> onErr);
}
