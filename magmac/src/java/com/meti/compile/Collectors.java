package com.meti.compile;

import com.meti.Collector;

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
}
