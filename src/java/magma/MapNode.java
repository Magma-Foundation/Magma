package magma;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}