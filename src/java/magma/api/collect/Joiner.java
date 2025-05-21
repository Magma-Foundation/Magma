package magma.api.collect;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    public static Joiner empty() {
        return new Joiner("");
    }

    @Override
    public Option<String> createInitial() {
        return new None<String>();
    }

    @Override
    public Option<String> fold(Option<String> maybe, String element) {
        return new Some<String>(maybe.map((String inner) -> {
            return inner + this.delimiter + element;
        }).orElse(element));
    }
}
