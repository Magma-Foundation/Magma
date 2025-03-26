package magma;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class MapNode {
    private final Map<String, String> strings;

    public MapNode(Map<String, String> strings) {
        this.strings = strings;
    }

    public MapNode() {
        this(new HashMap<>());
    }

    public MapNode withString(String propertyKey, String propertyValue) {
        strings.put(propertyKey, propertyValue);
        return this;
    }

    public Optional<String> find(String propertyKey) {
        return Optional.ofNullable(strings.get(propertyKey));
    }
}