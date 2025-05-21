package magma.app.compile.compose;

import magma.api.option.Option;

import java.util.function.Function;

public interface Composable<T, R> {
    Option<R> apply(T element);
}
