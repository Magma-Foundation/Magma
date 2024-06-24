package magma.java;

import magma.api.Tuple;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.Optional;

public class JavaOptionals {
    public static <T> Option<T> fromNative(Optional<T> value) {
        return value
                .<Option<T>>map(Some::new)
                .orElse(new None<>());
    }

    public static <T> Optional<T> toNative(Option<T> value) {
        return value
                .map(Optional::of)
                .orElseGet(Optional::empty);
    }

    public static <C> Tuple<Boolean, C> toTuple(Optional<C> optional, C other) {
        return optional.map(value -> new Tuple<>(true, value)).orElse(new Tuple<>(false, other));
    }
}
