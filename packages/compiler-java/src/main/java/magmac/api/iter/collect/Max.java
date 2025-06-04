package magmac.api.iter.collect;

import java.util.Optional;

/**
 * Collector that determines the maximum integer.
 */

public record Max() implements Collector<Integer, Optional<Integer>> {
    @Override
    public Optional<Integer> createInitial() {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> fold(Optional<Integer> current, Integer element) {
        return Optional.of(current.filter((Integer inner) -> inner > element).orElse(element));
    }
}
