package magma.api;

import java.util.Optional;

public class Options {
    public static <C> Tuple<Boolean, C> toTuple(Optional<C> optional, C other) {
        return optional.map(value -> new Tuple<>(true, value)).orElse(new Tuple<>(false, other));
    }
}
