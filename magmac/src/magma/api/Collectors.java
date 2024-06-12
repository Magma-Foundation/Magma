package magma.api;

import java.util.Optional;

public class Collectors {
    public static Collector<String, Optional<String>> joining() {
        return new Collector<String, Optional<String>>() {
        };
    }

    public static <T, C, E> Collector<Result<T, E>, Result<C, E>> exceptionally(Collector<T, C> collector) {
        return new Collector<Result<T, E>, Result<C, E>>() {
        };
    }
}
