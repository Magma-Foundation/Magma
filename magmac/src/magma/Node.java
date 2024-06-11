package magma;

import java.util.Optional;
import java.util.stream.Stream;

public interface Node {
    Node with(String key, String value);

    Optional<String> apply(String key);

    Node merge(Node other);

    Stream<Tuple> streamEntries();
}
