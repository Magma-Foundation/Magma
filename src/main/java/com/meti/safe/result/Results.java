package com.meti.safe.result;

import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class Results {
    public static <T, E extends Throwable> Option<Result<T, E>> invert(Result<Option<T>, E> result) {
        return result.matchWithValue(tOption -> tOption.map(Ok::apply), e -> Some.apply(Err.apply(e)));
    }

    public static <T, E extends Throwable> Result<T, E> flatten(Result<Result<T, E>, E> value) {
        return value.mapValueToResult(value1 -> value1);
    }
}
