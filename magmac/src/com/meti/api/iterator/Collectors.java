package com.meti.api.iterator;

import com.meti.api.result.Ok;
import com.meti.api.result.Result;

public class Collectors {
    public static Collector<String, String> joining() {
        return new Collector<>() {
            @Override
            public String initial() {
                return "";
            }

            @Override
            public String fold(String current, String element) {
                return current + element;
            }
        };
    }

    public static <T, R, E extends Throwable> Collector<Result<T, E>, Result<R, E>> exceptionally(Collector<T, R> collector) {
        return new Collector<>() {
            @Override
            public Result<R, E> initial() {
                return Ok.apply(collector.initial());
            }

            @Override
            public Result<R, E> fold(Result<R, E> current, Result<T, E> element) {
                return current.mapValueExceptionally(innerCurrent -> element.mapValue(innerElement -> collector.fold(innerCurrent, innerElement)));
            }
        };
    }
}
