package magma;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public final class MapNode implements Node {
    private final Map<String, String> strings;

    public MapNode(Map<String, String> strings) {
        this.strings = strings;
    }

    public MapNode() {
        this(new HashMap<>());
    }

    @Override
    public Node withString(String propertyKey, String propertyValue) {
        strings.put(propertyKey, propertyValue);
        return this;
    }

    @Override
    public Optional<String> find(String propertyKey) {
        return Optional.ofNullable(strings.get(propertyKey));
    }

    @Override
    public Node merge(Node other) {
        return other.stream().<Node>reduce(this,
                (current, entry) -> current.withString(entry.left(), entry.right()),
                (_, next) -> next);
    }

    @Override
    public Stream<Tuple<String, String>> stream() {
        return strings.entrySet()
                .stream()
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }
}