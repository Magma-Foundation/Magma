package com.meti.collect.result;

import com.meti.collect.Action;
import com.meti.collect.Pair;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

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

    public static <A, B, E extends Throwable> Result<Pair<A, B>, E> invertTupleByRight(Pair<A, Result<B, E>> pair) {
        return pair.right().mapValue(b -> new Pair<>(pair.left(), b));
    }

    public static <T, E extends Throwable> Stream<Result<T, E>> invertStream(Result<Stream<T>, E> result) {
        return result.match(tStream -> tStream.map(Ok::Ok), err -> Streams.from(Err(err)));
    }

    public static <T, E extends Throwable> Result<T, E> flatten(Result<Result<T, E>, E> result) {
        return result.match(value -> value, Err::Err);
    }
}
