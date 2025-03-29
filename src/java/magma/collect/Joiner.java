package magma.collect;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    @Override
    public Option<String> createInitial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> maybeCurrent, String element) {
        return new Some<>(maybeCurrent.map(inner -> inner + delimiter + element).orElse(element));
    }
}
