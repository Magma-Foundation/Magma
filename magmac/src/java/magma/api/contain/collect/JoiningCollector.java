package magma.api.contain.collect;

import java.util.Optional;

public record JoiningCollector(String delimiter) implements Collector<String, Optional<String>> {
    @Override
    public Optional<String> createInitial() {
        return Optional.empty();
    }

    @Override
    public Optional<String> fold(Optional<String> current, String next) {
        return current.isEmpty()
                ? Optional.of(next)
                : current.map(inner -> inner + delimiter + next);
    }
}
