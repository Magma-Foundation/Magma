package com.meti;

class Results {


    public static <E extends Exception> Option<E> asOption(Action<E> action) {
        try {
            action.perform();
            return new None<>();
        } catch (Exception e) {
            //noinspection unchecked
            return new Some<>((E) e);
        }
    }

    public static <T, E extends Exception> T unwrap(Result<T, E> result) throws E {
        var value = result.value();
        var err = result.err();

        if (value.isPresent()) return value.unwrapOrPanic();
        if (err.isPresent()) throw err.unwrapOrPanic();
        throw new RuntimeException("Neither the value or err is present.");
    }

    interface Action<E extends Exception> {
        void perform() throws E;
    }
}
