package magma;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record MapNode(Map<String, String> values) implements Node {
    public MapNode() {
        this(Collections.emptyMap());
    }

    @Override
    public Node with(String key, String value) {
        var copy = new HashMap<>(values);
        copy.put(key, value);
        return new MapNode(copy);
    }

    @Override
    public Optional<String> apply(String key) {
        return values.containsKey(key)
                ? Optional.of(values.get(key))
                : Optional.empty();
    }

    @Override
    public Node merge(Node other) {
        var entries = other.streamEntries().collect(Collectors.toSet());

        Node current = this;
        for (Tuple entry : entries) {
            current = current.with(entry.left(), entry.right());
        }

        return current;
    }

    @Override
    public Stream<Tuple> streamEntries() {
        return values.entrySet()
                .stream()
                .map(entry -> new Tuple(entry.getKey(), entry.getValue()));
    }
}
