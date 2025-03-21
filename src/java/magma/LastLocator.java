package magma;

import java.util.Optional;

public class LastLocator implements Locator {
    @Override
    public Optional<Integer> locate(String input, String slice) {
        final var index = input.lastIndexOf(slice);
        if (index < 0) return Optional.empty();
        return Optional.of(index);
    }
}