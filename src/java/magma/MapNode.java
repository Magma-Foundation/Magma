package magma;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MapNode {
    private final Optional<String> type;
    private final Map<String, String> strings;
    private final Map<String, List<String>> stringLists;

    public MapNode() {
        this(Optional.empty(), new HashMap<>(), new HashMap<>());
    }

    public MapNode(Optional<String> type, Map<String, String> strings, Map<String, List<String>> stringLists) {
        this.type = type;
        this.stringLists = stringLists;
        this.strings = strings;
    }

    public MapNode withStringList(String propertyKey, List<String> propertyValues) {
        stringLists.put(propertyKey, propertyValues);
        return this;
    }

    public MapNode withString(String propertyKey, String propertyValue) {
        strings.put(propertyKey, propertyValue);
        return this;
    }

    public Optional<String> findString(String propertyKey) {
        return Optional.ofNullable(strings.get(propertyKey));
    }

    public Optional<List<String>> findStringList(String propertyKey) {
        return Optional.ofNullable(stringLists.get(propertyKey));
    }

    public MapNode withType(String type) {
        return new MapNode(Optional.of(type), strings, stringLists);
    }

    public boolean is(String type) {
        return this.type.isPresent() && this.type.get().equals(type);
    }
}