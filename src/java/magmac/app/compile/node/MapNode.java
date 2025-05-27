package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.Iter;
import magmac.api.collect.Iters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MapNode implements Node {
    private final Optional<String> maybeType;
    private final Map<String, String> strings;
    private final Map<String, Node> nodes;
    private final Map<String, List<Node>> nodeLists;

    public MapNode() {
        this(Optional.empty(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public MapNode(Optional<String> maybeType, Map<String, String> strings, Map<String, Node> nodes, Map<String, List<Node>> nodeLists) {
        this.strings = strings;
        this.maybeType = maybeType;
        this.nodes = nodes;
        this.nodeLists = nodeLists;
    }

    public MapNode(String type) {
        this(Optional.of(type), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    @Override
    public String toString() {
        return "MapNode{" +
                "maybeType=" + this.maybeType +
                ", strings=" + this.strings +
                ", nodes=" + this.nodes +
                ", nodeLists=" + this.nodeLists +
                '}';
    }

    @Override
    public Iter<Tuple2<String, List<Node>>> iterNodeLists() {
        return Iters.fromMap(this.nodeLists);
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
        return new MapNode(Optional.of(type), this.strings, this.nodes, this.nodeLists);
    }

    @Override
    public Node withNodeList(String key, List<Node> values) {
        this.nodeLists.put(key, values);
        return this;
    }

    @Override
    public Optional<List<Node>> findNodeList(String key) {
        if (this.nodeLists.containsKey(key)) {
            return Optional.of(this.nodeLists.get(key));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Node withNode(String key, Node value) {
        this.nodes.put(key, value);
        return this;
    }

    @Override
    public Optional<Node> findNode(String key) {
        if (this.nodes.containsKey(key)) {
            return Optional.of(this.nodes.get(key));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, Node> nodes() {
        return this.nodes;
    }

    @Override
    public Map<String, List<Node>> nodeLists() {
        return this.nodeLists;
    }

    @Override
    public Node merge(Node other) {
        this.strings.putAll(other.strings());
        this.nodes.putAll(other.nodes());
        this.nodeLists.putAll(other.nodeLists());
        return this;
    }
}