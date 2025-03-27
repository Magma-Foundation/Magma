package magma;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, X> {
    Optional<T> findValue();

    Optional<X> findError();

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    boolean isOk();

    <R> Result<T, R> mapErr(Function<X, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
}
