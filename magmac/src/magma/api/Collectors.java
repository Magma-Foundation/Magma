package magma.api;

import java.util.Optional;

public class Collectors {
    public static Collector<String, Optional<String>> joining() {
        return new Collector<>() {
            @Override
            public Optional<String> createInitial() {
                return Optional.empty();
            }

            @Override
            public Optional<String> fold(Optional<String> current, String next) {
                return current.isEmpty()
                        ? Optional.of(next)
                        : current.map(inner -> inner + next);
            }
        };
    }

    public static <T, C, E> Collector<Result<T, E>, Result<C, E>> exceptionally(Collector<T, C> collector) {
        return new Collector<Result<T, E>, Result<C, E>>() {
            @Override
            public Result<C, E> createInitial() {
                return new Ok<>(collector.createInitial());
            }

            @Override
            public Result<C, E> fold(Result<C, E> current, Result<T, E> next) {
                return current.flatMapValue(inner -> next.mapValue(inner0 -> collector.fold(inner, inner0)));
            }
        };
    }
}