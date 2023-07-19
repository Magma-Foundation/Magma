package com.meti.core;

public class Results {
    public static <T, E extends Exception> T unwrap(Result<T, E> result) throws E {
        var value = result.value();
        var err = result.err();

        if (value.isPresent()) return value.unwrap();
        if (err.isPresent()) throw err.unwrap();
        throw new RuntimeException("Neither the unwrap or err is present.");
    }

    public static <T, E extends Exception> Result<T, E> $Result(Class<E> eClass, Generator<T, E> generator) {
        try {
            return Ok.apply(generator.generate());
        } catch (Exception e) {
            try {
                return Err.apply(eClass.cast(e));
            } catch (ClassCastException ex) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static <E extends Exception> void throwOption(Option<E> option) throws E {
        if (option.isPresent()) {
            throw option.unwrap();
        }
    }
}
