package com.meti.iterate;

import com.meti.core.Option;
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

    public static <T, C> Collector<Option<T>, Option<C>> and(Collector<T, C> collector) {
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
