package magma.app;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.text.Strings;
import magma.app.compile.compose.Composable;

public record PrefixRule<T>(String prefix, Composable<String, T> mapper) implements Composable<String, T> {
    @Override
    public Option<T> apply(String input) {
        if (!input.startsWith(this.prefix())) {
            return new None<>();
        }

        var slice = Strings.sliceFrom(input, Strings.length(this.prefix()));
        return this.mapper().apply(slice);
    }
}