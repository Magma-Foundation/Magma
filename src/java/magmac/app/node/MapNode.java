package magmac.app.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class MapNode implements Node {
    private final Map<String, String> strings;
    private final Optional<String> maybeType;

    public MapNode() {
        this(Optional.empty(), new HashMap<>());
    }

    public MapNode(Optional<String> maybeType, Map<String, String> strings) {
        this.strings = strings;
        this.maybeType = maybeType;
    }

    public MapNode(String type) {
        this(Optional.of(type), new HashMap<>());
    }

    @Override
    public Node withString(String key, String value) {
        this.strings.put(key, value);
        return this;
    }

    @Override
    public Optional<String> findString(String key) {
        if (this.strings.containsKey(key)) {
            return Optional.of(this.strings.get(key));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, String> strings() {
        return this.strings;
    }

    @Override
    public boolean is(String type) {
        return this.maybeType.filter(inner -> inner.equals(type)).isPresent();
    }

    @Override
    public Node retype(String type) {
        return new MapNode(Optional.of(type), this.strings);
    }

    @Override
    public Node merge(Node other) {
        this.strings.putAll(other.strings());
        return this;
    }
}