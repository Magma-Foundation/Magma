package com.meti.collect.stream;

import com.meti.collect.option.Option;
import com.meti.collect.result.Ok;
import com.meti.collect.result.Result;
import com.meti.compile.CompileException;
import com.meti.compile.node.Node;

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
}
