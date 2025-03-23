package magma.option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Option<T> permits Some, None {
    default <R> R into(Function<Option<T>, R> mapper) {
        return mapper.apply(this);
    }

    <R> Option<R> map(Function<T, R> mapper);

    T orElseGet(Supplier<T> other);

    boolean isPresent();

    void ifPresent(Consumer<T> consumer);
}
