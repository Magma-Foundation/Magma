package magma.api.result;

import magma.api.option.Option;

import java.util.function.Function;

public interface Result<T, X> {
    Option<X> findError();

    Option<T> findValue();

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    <R> Result<R, X> mapValue(Function<T, R> mapper);
}
