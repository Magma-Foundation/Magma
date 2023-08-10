package com.meti.iterate;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.core.Some;

public class Collectors {
    public static Collector<Boolean, Boolean> or() {
        return new Collector<>() {
            @Override
            public Boolean initial() {
                return false;
            }

            @Override
            public Boolean foldLeft(Boolean accumulated, Boolean element) {
                return accumulated || element;
            }
        };
    }

    public static <T, E extends Throwable, C> Collector<Result<T, E>, Result<C, E>> exceptionally(Collector<T, C> collector) {
        return new Collector<>() {
            @Override
            public Result<C, E> initial() {
                return Ok.apply(collector.initial());
            }

            @Override
            public Result<C, E> foldLeft(Result<C, E> accumulated, Result<T, E> element) {
                return accumulated.mapValueToResult(
                        accumulatedValue -> element.mapValue(
                                elementValue -> collector.foldLeft(accumulatedValue, elementValue)));
            }
        };
    }

    public static <T, C> Collector<Option<T>, Option<C>> andRequireAll(Collector<T, C> collector) {
        return new Collector<>() {
            @Override
            public Option<C> initial() {
                return Some.apply(collector.initial());
            }

            @Override
            public Option<C> foldLeft(Option<C> accumulated, Option<T> element) {
                return accumulated.flatMap(
                        value -> element.map(
                                valueElement -> collector.foldLeft(value, valueElement)));
            }
        };
    }
}
