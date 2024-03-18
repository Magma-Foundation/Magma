package com.meti.collect.stream;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Tuple;
import com.meti.collect.option.Option;
import com.meti.collect.result.Ok;
import com.meti.collect.result.Result;
import com.meti.java.JavaString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class Collectors {
    public static <T> Collector<T, List<T>> toNativeList() {
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

    public static Collector<String, Option<String>> joiningNatively() {
        return joiningNatively("");
    }

    public static Collector<String, Option<String>> joiningNatively(String delimiter) {
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

    public static Collector<JavaString, Option<JavaString>> joining() {
        return joining(JavaString.Empty);
    }

    public static Collector<JavaString, Option<JavaString>> joining(JavaString delimiter) {
        return new Collector<>() {
            @Override
            public Option<JavaString> initial() {
                return None();
            }

            @Override
            public Option<JavaString> fold(Option<JavaString> current, JavaString element) {
                if (current.isEmpty()) return Some(element);

                return current.map(currentValue -> currentValue.concatOwned(delimiter).concatOwned(element));
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

    public static <T> Collector<T, JavaList<T>> toList() {
        return new Collector<>() {
            @Override
            public JavaList<T> initial() {
                return new JavaList<>();
            }

            @Override
            public JavaList<T> fold(JavaList<T> current, T element) {
                return current.add(element);
            }
        };
    }

    public static Collector<Boolean, Boolean> allTrue() {
        return new Collector<>() {
            @Override
            public Boolean initial() {
                return true;
            }

            @Override
            public Boolean fold(Boolean current, Boolean element) {
                return current && element;
            }
        };
    }

    public static <K, V> Collector<Tuple<K, V>, JavaMap<K, V>> toOverridingMap() {
        return toMap((v, v2) -> v2);
    }

    public static <K, V> Collector<Tuple<K, V>, JavaMap<K, V>> toMap(BiFunction<V, V, V> merger) {
        return new Collector<>() {
            @Override
            public JavaMap<K, V> initial() {
                return new JavaMap<>();
            }

            @Override
            public JavaMap<K, V> fold(JavaMap<K, V> current, Tuple<K, V> element) {
                return current.merge(element.a(), element.b(), merger);
            }
        };
    }
}
