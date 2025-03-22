package magma;

import java.util.Optional;

public interface StringRule {
    Optional<String> apply(String input);
}
