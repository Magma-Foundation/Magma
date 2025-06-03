package magmac.api.result;

import magmac.api.Tuple2;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, X> {
    <R> Result<R, X> mapValue(Function<T, R> mapper);

    <R> Result<Tuple2<T, R>, X> and(Supplier<Result<R, X>> supplier);

    <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

    <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

    <R> Result<T, R> mapErr(Function<X, R> mapper);
}
