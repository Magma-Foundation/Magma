package com.meti;

import java.util.ArrayList;
import java.util.List;

import static com.meti.None.None;
import static com.meti.Some.Some;

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
        return new Collector<>() {
            @Override
            public Option<String> initial() {
                return None();
            }

            @Override
            public Option<String> fold(Option<String> current, String element) {
                if(current.isEmpty()) return Some(element);

                return current.map(currentValue -> currentValue + element);
            }
        };
    }
}
