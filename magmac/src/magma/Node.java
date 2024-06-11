package magma;

import java.util.Optional;

public interface Node {
    Node with(String key, String value);

    Optional<String> apply(String key);
}
