package magmac.app.compile.node;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.iter.collect.Joiner;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class MapNode implements Node {
    private final Option<String> maybeType;
    private final Map<String, String> strings;
    private final Map<String, Node> nodes;
    private final Map<String, NodeList> nodeLists;

    public MapNode() {
        this(new None<String>(), Maps.empty(), Maps.empty(), Maps.empty());
    }

    private MapNode(
            Option<String> maybeType,
            Map<String, String> strings,
            Map<String, Node> nodes,
            Map<String, NodeList> nodeLists
    ) {
        this.strings = strings;
        this.maybeType = maybeType;
        this.nodes = nodes;
        this.nodeLists = nodeLists;
    }

    public MapNode(String type) {
        this(new Some<String>(type), Maps.empty(), Maps.empty(), Maps.empty());
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
        return this.nodes.iterEntries();
    }

    @Override
    public String display() {
        return this.format(0);
    }

    @Override
    public String format(int depth) {
        String typeString = this.maybeType
                .map(type -> type + " ")
                .orElse("");

        Iter<String> stringsStream = this.toStream(depth, this.strings, value -> "\"" + value + "\"");
        Iter<String> nodesStream = this.toStream(depth, this.nodes, value -> value.format(depth + 1));
        Iter<String> nodeListsStream = this.toStream(depth, this.nodeLists, values -> "[" + this.formatNodeList(depth, values) + "]");

        String joined = stringsStream.concat(nodesStream)
                .concat(nodeListsStream)
                .collect(new Joiner(", "))
                .orElse("");

        return typeString + "{" + joined + this.createIndent(depth) + "}";
    }

    private <T> Iter<String> toStream(int depth, Map<String, T> map, Function<T, String> mapper) {
        if (map.isEmpty()) {
            return Iters.empty();
        }

        return map.iterEntries().map(entry -> {
            String key = entry.left();
            T value = entry.right();
            return this.formatEntry(depth, key, mapper.apply(value));
        });
    }

    private String formatNodeList(int depth, NodeList nodeList) {
        return nodeList.iter()
                .map(child -> child.format(depth + 1))
                .collect(new Joiner(", "))
                .orElse("");
    }

    private String formatEntry(int depth, String key, String value) {
        return this.createIndent(depth + 1) + key + ": " + value;
    }

    private String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    @Override
    public Node withString(String key, String value) {
        this.strings.put(key, value);
        return this;
    }

    @Override
    public Option<String> findString(String key) {
        if (this.strings.containsKey(key)) {
            return new Some<>(this.strings.get(key));
        }
        else {
            return new None<>();
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
        return new MapNode(new Some<String>(type), this.strings, this.nodes, this.nodeLists);
    }

    @Override
    public Node withNode(String key, Node value) {
        this.nodes.put(key, value);
        return this;
    }

    @Override
    public Option<Node> findNode(String key) {
        if (this.nodes.containsKey(key)) {
            return new Some<>(this.nodes.get(key));
        }
        else {
            return new None<>();
        }
    }

    @Override
    public Node merge(Node other) {
        var withStrings = MapNode.fold(this, other.iterStrings(), current -> current::withString);
        var withNodes = MapNode.fold(withStrings, other.iterNodes(), current -> current::withNode);
        return MapNode.fold(withNodes, other.iterNodeLists(), current -> (key, values) -> current.withNodeList(key, values));
    }

    @Override
    public Iter<Tuple2<String, String>> iterStrings() {
        return this.strings().iterEntries();
    }

    @Override
    public Iter<Tuple2<String, NodeList>> iterNodeLists() {
        return this.nodeLists.iterEntries();
    }

    @Override
    public Node withNodeList(String key, NodeList values) {
        this.nodeLists.put(key, values);
        return this;
    }

    @Override
    public Option<NodeList> findNodeList(String key) {
        if (this.nodeLists.containsKey(key)) {
            return new Some<>(this.nodeLists.get(key));
        }
        else {
            return new None<>();
        }
    }
}