package com.meti.api.option;

import com.meti.api.result.Action;
import com.meti.compile.JavaString;

import java.util.Optional;

public class Options {

    public static <T> Optional<T> toNative(Option<T> option) {
        return option.map(Optional::of).orElse(Optional.empty());
    }

    public static <T> Option<T> fromNative(Optional<T> javaString) {
        return javaString.<Option<T>>map(Some::new).orElseGet(None::new);
    }

    public static <T> Option<T> $Option(Action<T, OptionException> action) {
        try {
            return new Some<>(action.run());
        } catch (OptionException e) {
            return new None<>();
        }
    }
}
