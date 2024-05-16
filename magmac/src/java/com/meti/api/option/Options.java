package com.meti.api.option;

import java.util.Optional;

public class Options {

    public static <T> Optional<T> toNative(Option<T> option) {
        return option.map(Optional::of).orElse(Optional.empty());
    }

    public static <T> Option<T> fromNative(Optional<T> javaString) {
        return javaString.<Option<T>>map(Some::new).orElseGet(None::new);
    }
}
