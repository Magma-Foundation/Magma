package com.meti.util;

import java.util.Optional;

public class Options {
    public static <T> Option<T> $Option(Action<T, OptionException> action) {
        try {
            return new Some<>(action.act());
        } catch (OptionException e) {
            return new None<>();
        }
    }

    public static <T> Optional<T> toNative(Option<T> option) {
        return option.match(Optional::of, Optional::empty);
    }

    public static <T> T $$() throws OptionException {
        throw new OptionException();
    }

    public static <T> Option<T> fromNative(Optional<T> listOfNodes1) {
        return listOfNodes1.<Option<T>>map(Some::new).orElse(new None<>());
    }
}
