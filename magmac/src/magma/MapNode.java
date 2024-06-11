package magma;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}
