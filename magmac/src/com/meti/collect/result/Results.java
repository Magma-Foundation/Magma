package com.meti.collect.result;

import com.meti.collect.Action;

import static com.meti.collect.result.Err.Err;
import static com.meti.collect.result.Ok.Ok;

public class Results {
    public static <T, E extends Throwable> Result<T, E> $Result(Action<T, E> action) {
        try {
            return Ok(action.perform());
        } catch (Throwable e) {
            //noinspection unchecked
            return Err((E) e);
        }
    }
}
