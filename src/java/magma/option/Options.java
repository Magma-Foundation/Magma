package magma.option;

import java.util.Optional;

public class Options {
    public static <T> Optional<T> toNative(Option<T> option) {
        return option.map(Optional::of).orElseGet(Optional::empty);
    }

    public static <T> Option<T> fromNative(Optional<T> optional) {
        return optional.<Option<T>>map(Some::new).orElseGet(None::new);
    }
}
