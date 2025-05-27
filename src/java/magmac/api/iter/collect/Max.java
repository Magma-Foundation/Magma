package magmac.api.iter.collect;

import java.util.Optional;

public record Max() implements Collector<Integer, Optional<Integer>> {
    @Override
    public Optional<Integer> createInitial() {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> fold(Optional<Integer> current, Integer element) {
        return Optional.of(current.map(inner -> inner > element ? inner : element).orElse(element));
    }
}
