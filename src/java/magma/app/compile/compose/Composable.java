package magma.app.compile.compose;

import magma.api.Tuple2;
import magma.api.option.Option;

import java.util.function.BiFunction;

public interface Composable<T, R> {
    static <T> Composable<Tuple2<String, String>, T> toComposable(BiFunction<String, String, Option<T>> mapper) {
        return (Tuple2<String, String> tuple) -> {
            return mapper.apply(tuple.left(), tuple.right());
        };
    }

    Option<R> apply(T element);
}
