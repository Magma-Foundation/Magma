package magma.api.collect;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    @Override
    public Option<String> initial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> current, String next) {
        return new Some<>(current.map(inner -> inner + delimiter + next).orElse(next));
    }
}
