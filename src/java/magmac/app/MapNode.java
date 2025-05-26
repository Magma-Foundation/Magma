package magmac.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class MapNode {
    private final Map<String, String> strings;

    public MapNode() {
        this(new HashMap<>());
    }

    public MapNode(Map<String, String> strings) {
        this.strings = strings;
    }

    public MapNode withString(String key, String value) {
        this.strings.put(key, value);
        return this;
    }

    public Optional<String> findString(String key) {
        if (this.strings.containsKey(key)) {
            return Optional.of(this.strings.get(key));
        }
        else {
            return Optional.empty();
        }
    }
}