package com.meti;

import java.util.Optional;

public class Results {
    public static <T, E extends Throwable> Optional<Result<T, E>> unwrapOptional(Result<Optional<T>, E> result) {
        return result.match(optional -> optional.map(Ok::new), err -> Optional.of(new Err<>(err)));
    }
}
