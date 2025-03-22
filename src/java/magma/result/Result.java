package magma.result;

import java.util.function.Function;

public interface Result<T, X> {
    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
}
