package magma.result;

import magma.Tuple;
import magma.option.Option;

import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T, X> permits Ok, Err {
    Option<T> findValue();

    Option<X> findError();

    boolean isOk();

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<T, R> mapErr(Function<X, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
}

