package magma.java;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.Optional;

public class Options {
    public static <T> Optional<T> unwrap(Option<T> option) {
        return option.map(Optional::of).orElseGet(Optional::empty);
    }

    public static <T> Option<T> wrap(Optional<T> optional) {
        return optional.<Option<T>>map(Some::new).orElseGet(None::new);
    }
}
