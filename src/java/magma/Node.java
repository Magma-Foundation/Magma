package magma;

import java.util.Optional;
import java.util.stream.Stream;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Optional<String> find(String propertyKey);

    Node merge(Node other);

    Stream<Tuple<String, String>> stream();
}
