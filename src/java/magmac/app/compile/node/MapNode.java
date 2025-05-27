package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MapNode implements Node {
    private final Optional<String> maybeType;
    private final Map<String, String> strings;
    private final Map<String, Node> nodes;
    private final Map<String, List<Node>> nodeLists;

    public MapNode() {
        this(Optional.empty(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    private MapNode(Optional<String> maybeType, Map<String, String> strings, Map<String, Node> nodes, Map<String, List<Node>> nodeLists) {
        this.strings = strings;
        this.maybeType = maybeType;
        this.nodes = nodes;
        this.nodeLists = nodeLists;
    }

    public MapNode(String type) {
        this(Optional.of(type), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    private static <T> Node fold(Node node, Iter<Tuple2<String, T>> iter, Function<Node, BiFunction<String, T, Node>> mapper) {
        return iter.fold(node, (current, tuple) -> {
            String left = tuple.left();
            T right = tuple.right();

            return mapper.apply(current).apply(left, right);
        });
    }

    @Override
    public Iter<Tuple2<String, Node>> iterNodes() {
        return Iters.fromMap(this.nodes);
    }

    @Override
    public String display() {
        return format(0);
    }

    @Override
    public String format(int depth) {
        String typeString = this.maybeType
                .map(type -> type + " ")
                .orElse("");

        Stream<String> stringsStream = this.toStream(depth, this.strings, value -> "\"" + value + "\"");
        Stream<String> nodesStream = this.toStream(depth, this.nodes, value -> value.format(depth + 1));
        Stream<String> nodeListsStream = this.toStream(depth, this.nodeLists, values -> "[" + this.formatNodeList(depth, values) + "]");

        String joined = Stream.of(stringsStream, nodesStream, nodeListsStream)
                .flatMap(value -> value)
                .collect(Collectors.joining(", "));

        return typeString + "{" + joined + this.createIndent(depth) + "}";
    }

    private <T> Stream<String> toStream(int depth, Map<String, T> map, Function<T, String> mapper) {
        if (map.isEmpty()) {
            return Stream.empty();
        }

        return map.entrySet().stream().map(entry -> {
            String key = entry.getKey();
            T value = entry.getValue();
            return this.formatEntry(depth, key, mapper.apply(value));
        });
    }

    private String formatNodeList(int depth, List<Node> nodeList) {
        return nodeList.stream()
                .map(child -> child.format(depth + 1))
                .collect(Collectors.joining(", "));
    }

    private String formatEntry(int depth, String key, String value) {
        return this.createIndent(depth + 1) + key + ": " + value;
    }

    private String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
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

    private Map<String, String> strings() {
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
    public Node merge(Node other) {
        var withStrings = MapNode.fold(this, other.iterStrings(), current -> current::withString);
        var withNodes = MapNode.fold(withStrings, other.iterNodes(), current -> current::withNode);
        return MapNode.fold(withNodes, other.iterNodeLists(), current -> current::withNodeList);
    }

    @Override
    public Iter<Tuple2<String, String>> iterStrings() {
        return Iters.fromMap(this.strings());
    }
}