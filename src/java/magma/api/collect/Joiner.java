package magma.api.collect;

import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    public static Joiner empty() {
        return new Joiner("");
    }

    public static String joinOrEmpty(final List<String> items, final String delimiter, final String prefix, final String suffix) {
        return items.query()
                .collect(new Joiner(delimiter))
                .map((String inner) -> prefix + inner + suffix)
                .orElse("");
    }

    @Override
    public Option<String> createInitial() {
        return new None<String>();
    }

    @Override
    public Option<String> fold(final Option<String> maybe, final String element) {
        return new Some<String>(maybe.map((String inner) -> inner + this.delimiter + element).orElse(element));
    }
}
