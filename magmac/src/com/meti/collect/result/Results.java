package com.meti.collect.result;

import com.meti.collect.Action;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;

import static com.meti.collect.option.None.None;
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

    public static <T, E extends Throwable> Result<Option<T>, E> invertOption(Option<Result<T, E>> option) {
        return option.map(value -> value.mapValue(Some::Some)).orElse(Ok(None()));
    }
}
