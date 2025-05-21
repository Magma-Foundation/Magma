package magma.app.compile.compose;

import magma.api.Tuple2;
import magma.api.option.Option;
import magma.app.compile.split.Splitter;

import java.util.function.Function;

public record SplitComposable<T>(Splitter splitter, Composable<Tuple2<String, String>, T> mapper) implements Composable<String, T> {
    @Override
    public Option<T> apply(String input) {
        return this.splitter().apply(input).flatMap(this.mapper::apply);
    }
}