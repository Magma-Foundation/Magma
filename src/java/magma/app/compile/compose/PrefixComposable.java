package magma.app.compile.compose;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.text.Strings;

public record PrefixComposable<T>(String prefix, Composable<String, T> mapper) implements Composable<String, T> {
    @Override
    public Option<T> apply(String input) {
        if (!input.startsWith(this.prefix())) {
            return new None<T>();
        }

        var slice = Strings.sliceFrom(input, Strings.length(this.prefix()));
        return this.mapper().apply(slice);
    }
}