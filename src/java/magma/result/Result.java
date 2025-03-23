package magma.result;

import magma.Tuple;
import magma.option.Option;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Result<T, X> {
    Option<T> findValue();

    Option<X> findError();

    boolean isOk();

    <R> Result<Tuple<T, R>, X> and(Supplier<Result<R, X>> supplier);

    <R> Result<R, X> map(Function<T, R> mapper);
}
