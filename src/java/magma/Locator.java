package magma;

import java.util.Optional;

public interface Locator {
    Optional<Integer> locate(String slice, String input);
}
