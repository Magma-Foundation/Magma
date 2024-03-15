package com.meti.collect.stream;

import com.meti.collect.option.Option;
import com.meti.collect.result.Ok;
import com.meti.collect.result.Result;

import java.util.ArrayList;
import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class Collectors {
    public static <T> Collector<T, List<T>> toList() {
        return new Collector<>() {
            @Override
            public List<T> initial() {
                return new ArrayList<>();
            }

            @Override
            public List<T> fold(List<T> current, T element) {
                current.add(element);
                return current;
            }
        };
    }

    public static Collector<String, Option<String>> joining() {
        return joining("");
    }

    public static Collector<String, Option<String>> joining(String delimiter) {
        return new Collector<>() {
            @Override
            public Option<String> initial() {
                return None();
            }

            @Override
            public Option<String> fold(Option<String> current, String element) {
                if (current.isEmpty()) return Some(element);

                return current.map(currentValue -> currentValue + delimiter + element);
            }
        };
    }

    public static <T, C, E extends Throwable> Collector<Result<T, E>, Result<C, E>> exceptionally(Collector<T, C> parent) {
        return new Collector<>() {
            @Override
            public Result<C, E> initial() {
                return Ok.Ok(parent.initial());
            }

            @Override
            public Result<C, E> fold(Result<C, E> current, Result<T, E> element) {
                return current.and(element).mapValue(tuple -> parent.fold(tuple.a(), tuple.b()));
            }
        };
    }

    public static <T, C> Collector<Option<T>, Option<C>> required(Collector<T, C> list) {
        return new Collector<>() {
            @Override
            public Option<C> initial() {
                return Some(list.initial());
            }

            @Override
            public Option<C> fold(Option<C> current, Option<T> element) {
                return current.and(element).map(tuple -> list.fold(tuple.a(), tuple.b()));
            }
        };
    }
}
