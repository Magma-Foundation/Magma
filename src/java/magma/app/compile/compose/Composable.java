package magma.app.compile.compose;

import magma.api.option.Option;

public interface Composable<T, R> {
    Option<R> apply(T element);
}
