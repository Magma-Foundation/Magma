package magma.app.compile.compose;

import magma.api.Tuple2;
import magma.api.option.Option;
import magma.app.compile.locate.LastLocator;
import magma.app.compile.split.LocatingSplitter;
import magma.app.compile.split.Splitter;

import java.util.function.BiFunction;

public record SplitComposable<T>(Splitter splitter,
                                 Composable<Tuple2<String, String>, T> mapper) implements Composable<String, T> {
    public static <T> Option<T> compileLast(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        Splitter splitter1 = new LocatingSplitter(infix, new LastLocator());
        return new SplitComposable<T>(splitter1, Composable.toComposable(mapper)).apply(input);
    }

    @Override
    public Option<T> apply(String input) {
        return this.splitter().apply(input).flatMap(this.mapper::apply);
    }
}