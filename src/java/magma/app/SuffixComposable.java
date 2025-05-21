package magma.app;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.text.Strings;
import magma.app.compile.compose.Composable;

public record SuffixComposable<T>(String suffix, Composable<String, T> mapper) implements Composable<String, T> {
    @Override
    public Option<T> apply(String input) {
        if (!input.endsWith(this.suffix())) {
            return new None<T>();
        }

        var length = Strings.length(input);
        var length1 = Strings.length(this.suffix());
        var content = Strings.sliceBetween(input, 0, length - length1);
        return this.mapper().apply(content);
    }
}