package com.meti.safe;

import com.meti.safe.option.Option;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;

import java.util.function.Function;

public record Tuple2<A, B>(A left, B right) {
    public static <A, B, E extends Throwable> Result<Tuple2<A, B>, E> unwrapRight(Tuple2<A, Result<B, E>> tuple2) {
        return tuple2.right.matchWithValue(right -> Ok.apply(new Tuple2<>(tuple2.left, right)), Err::apply);
    }

    public <R> Option<Tuple2<A, R>> mapRightOptionally(Function<B, Option<R>> mapper) {
        return mapper.apply(right).map(newRight -> new Tuple2<>(left, newRight));
    }
}
