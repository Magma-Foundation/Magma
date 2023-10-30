package com.meti.api.result;

public class Results {
    public static <T, E extends Throwable> Result<T, E> $Result(ResultAction<T, E> action) {
        try {
            return Ok.apply(action.act());
        } catch (Throwable e) {
            //noinspection unchecked
            return Err.apply((E) e);
        }
    }

    public interface ResultAction<T, E extends Throwable> {
        T act() throws E;
    }
}
