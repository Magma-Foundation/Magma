package com.meti.compile;

import com.meti.api.Collector;
import com.meti.api.JavaList;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.ArrayList;
import java.util.List;

public class Collectors {
    public static Collector<String, Option<String>> joining() {
        return new Collector<>() {
            @Override
            public Option<String> initial() {
                return new None<>();
            }

            @Override
            public Option<String> fold(Option<String> current, String next) {
                return current.isEmpty() ? new Some<>(next) : current.map(value -> value + next);
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
            public JavaList<T> fold(JavaList<T> current, T next) {
                return current.add(next);
            }
        };
    }

    public static <T> Collector<T, List<T>> toNativeList() {
        return new Collector<>() {
            @Override
            public List<T> initial() {
                return new ArrayList<>();
            }

            @Override
            public List<T> fold(List<T> current, T next) {
                current.add(next);
                return current;
            }
        };
    }
}
