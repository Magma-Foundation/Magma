package magma.option;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option<T> {
    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    <R> Option<R> map(Function<T, R> mapper);

    T orElseGet(Supplier<T> other);
}
