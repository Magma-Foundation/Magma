package magma.api.result;

import magma.api.option.Option;

import java.util.function.Function;

public interface Result<T, X> {
    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    Option<X> findError();

    <R> Result<R, X> mapValue(Function<T, R> mapper);
}
