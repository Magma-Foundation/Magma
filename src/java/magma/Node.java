package magma;

import java.util.Optional;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Optional<String> find(String propertyKey);
}
