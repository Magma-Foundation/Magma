package magma.api;

import java.util.Optional;
import java.util.function.Function;

public interface Result<T, E> {
    Optional<T> findValue();

    Optional<E> findErr();

    <R> Result<R, E> flatMapValue(Function<T, Result<R, E>> mapper);

    <R> Result<R, E> mapValue(Function<T, R> mapper);

    boolean isOk();

    <R> Result<T, R> mapErr(Function<E, R> mapper);
}
