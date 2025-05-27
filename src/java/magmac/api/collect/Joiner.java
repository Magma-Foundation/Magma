package magmac.api.collect;

import java.util.Optional;

public record Joiner(String delimiter) implements Collector<String, Optional<String>> {
    public Joiner() {
        this("");
    }

    @Override
    public Optional<String> createInitial() {
        return Optional.empty();
    }

    @Override
    public Optional<String> fold(Optional<String> current, String element) {
        return Optional.of(current.map(inner -> inner + this.delimiter + element).orElse(element));
    }
}
