package magmac.api.collect;

import java.util.Optional;

public record Joiner() implements Collector<String, Optional<String>> {
    @Override
    public Optional<String> createInitial() {
        return Optional.empty();
    }

    @Override
    public Optional<String> fold(Optional<String> current, String element) {
        return Optional.of(current.map(inner -> inner + element).orElse(element));
    }
}
