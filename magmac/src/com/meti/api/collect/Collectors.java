package com.meti.api.collect;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import static com.meti.api.result.Results.$Result;

public class Collectors {
    private Collectors() {
    }

    public static <T, R, E extends Throwable> Collector<Result<T, E>, Result<R, E>> exceptionally(Collector<T, R> collector) {
        return new Collector<>() {
            @Override
            public Result<R, E> initial() {
                return Ok.apply(collector.initial());
            }

            @Override
            public Result<R, E> fold(Result<R, E> accumulated, Result<T, E> element) {
                return $Result(() -> {
                    var accumulatedValue = accumulated.$();
                    var elementValue = element.$();
                    return collector.fold(accumulatedValue, elementValue);
                });
            }
        };
    }

    public static Collector<JavaString, Option<JavaString>> joining() {
        return joining(JavaString.Empty);
    }

    public static Collector<JavaString, Option<JavaString>> joining(final JavaString delimiter) {
        return new Collector<>() {
            @Override
            public Option<JavaString> initial() {
                return None.apply();
            }

            @Override
            public Option<JavaString> fold(Option<JavaString> accumulated, JavaString element) {
                if (accumulated.isEmpty()) {
                    return Some.apply(element);
                } else {
                    return accumulated.map(accumulatedValue -> accumulatedValue.concat(delimiter).concat(element));
                }
            }
        };
    }

}
