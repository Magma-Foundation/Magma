package magma;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, X> {
    Optional<T> findValue();

    Optional<X> findError();

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> other);

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
}
