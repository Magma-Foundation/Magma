package magma.api.result;

import magma.api.option.Option;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, X> {
    Option<T> findValue();

    Option<X> findError();

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

    <R> Result<T, R> mapErr(Function<X, R> mapper);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
}
