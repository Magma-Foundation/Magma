package com.meti.api.iterate;

import com.meti.api.collect.JavaString;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import static com.meti.api.result.Results.$Result;

public class Collectors {
    public static <T, R, E extends Throwable> Collector<Result<T, E>, Result<R, E>> fromResult(Collector<T, R> collector) {
        return new Collector<>() {
            @Override
            public Result<R, E> initial() {
                return Ok.apply(collector.initial());
            }

            @Override
            public Result<R, E> fold(Result<R, E> accumulated, Result<T, E> value) {
                return $Result(() -> {
                    var acc = accumulated.$();
                    var element = value.$();
                    return collector.fold(acc, element);
                });
            }
        };
    }

    public static Collector<JavaString, JavaString> join() {
        return new Collector<>() {
            @Override
            public JavaString initial() {
                return JavaString.Empty;
            }

            @Override
            public JavaString fold(JavaString accumulated, JavaString value) {
                return accumulated.concat(value);
            }
        };
    }
}