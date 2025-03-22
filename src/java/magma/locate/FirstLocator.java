package magma.locate;

import java.util.Optional;

public class FirstLocator implements Locator {
    @Override
    public Optional<Integer> locate(String input, String slice) {
        final var index = input.indexOf(slice);
        if (index < 0) return Optional.empty();
        return Optional.of(index);
    }
}