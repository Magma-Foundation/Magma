package magma;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Node {
    private final Map<String, String> strings;
    private final Map<String, List<Node>> nodeLists;
    private final Optional<String> maybeType;

    public Node() {
        this(Optional.empty(), new HashMap<>(), new HashMap<>());
    }

    public Node(Optional<String> maybeType, Map<String, String> strings, Map<String, List<Node>> nodeLists) {
        this.strings = strings;
        this.nodeLists = nodeLists;
        this.maybeType = maybeType;
    }

    public Node(String type) {
        this(Optional.of(type), new HashMap<>(), new HashMap<>());
    }

    public Node withString(String propertyKey, String propertyValue) {
        strings.put(propertyKey, propertyValue);
        return this;
    }

    public Optional<String> findString(String propertyKey) {
        return Optional.ofNullable(strings.get(propertyKey));
    }

    public Node withNodeList(String propertyKey, List<Node> propertyValues) {
        nodeLists.put(propertyKey, propertyValues);
        return this;
    }

    public Optional<List<Node>> findNodeList(String propertyKey) {
        return Optional.ofNullable(nodeLists.get(propertyKey));
    }

    public boolean is(String type) {
        return this.maybeType.isPresent() && this.maybeType.get().equals(type);
    }
}
