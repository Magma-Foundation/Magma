package magmac.api.iter.collect;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    public Joiner() {
        this("");
    }

    @Override
    public Option<String> createInitial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> current, String element) {
        return new Some<>(current.map((String inner) -> inner + this.delimiter + element).orElse(element));
    }
}
