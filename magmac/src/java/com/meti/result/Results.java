package com.meti.result;

import java.util.Optional;

public class Results {
    public static <T, E extends Throwable> Result<T, E> $Result(Action<T, E> action) {
        try {
            return new Ok<>(action.run());
        } catch (Throwable e) {
            //noinspection unchecked
            return new Err<>((E) e);
        }
    }

    public static <T, E extends Throwable> Optional<Result<T, E>> unwrapOptional(Result<Optional<T>, E> result) {
        return result.match(optional -> optional.map(Ok::new), err -> Optional.of(new Err<>(err)));
    }
}
