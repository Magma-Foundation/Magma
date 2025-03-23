package magma.java.option;

import magma.option.Option;

import java.util.Optional;

public class JavaOptions {
    public static <T> Optional<T> toNative(Option<T> option) {
        return option.map(Optional::of).orElseGet(Optional::empty);
    }

}
