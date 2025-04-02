package magma.result;

import magma.Tuple;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, X> {
    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    Optional<T> findValue();

    Optional<X> findError();

    boolean isOk();
}
