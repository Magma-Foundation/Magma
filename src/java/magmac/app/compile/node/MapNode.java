package magmac.app.compile.node;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.Joiner;
import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.error.CompileErrors;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class MapNode implements Node {
    private final Option<String> maybeType;
    private Map<String, NodeList> nodeLists;
    private Map<String, Node> nodes;
    private Map<String, String> strings;

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
        return iter.fold(node, (Node current, Tuple2<String, T> tuple) -> {
            String left = tuple.left();
            T right = tuple.right();

            return mapper.apply(current).apply(left, right);
        });
    }

    private static String formatNodeList(int depth, NodeList nodeList) {
        return nodeList.iter()
                .map((Node child) -> child.format(depth + 1))
                .collect(new Joiner(", "))
                .orElse("");
    }

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    }

    private static String formatEntry(int depth, String key, String value) {
        String indent = MapNode.createIndent(depth + 1);
        return indent + key + ": " + value;
    }

    private static <T> Iter<String> toStream(int depth, Map<String, T> map, Function<T, String> mapper) {
        if (map.isEmpty()) {
            return Iters.empty();
        }

        return map.iter().map((Tuple2<String, T> entry) -> {
            String key = entry.left();
            T value = entry.right();
            return MapNode.formatEntry(depth, key, mapper.apply(value));
        });
    }

    @Override
    public Iter<Tuple2<String, Node>> iterNodes() {
        return this.nodes.iter();
    }

    @Override
    public String display() {
        return this.format(0);
    }

    @Override
    public String format(int depth) {
        String typeString = this.maybeType
                .map((String type) -> type + " ")
                .orElse("");

        Iter<String> stringsStream = MapNode.toStream(depth, this.strings, (String value) -> "\"" + value + "\"");
        Iter<String> nodesStream = MapNode.toStream(depth, this.nodes, (Node value) -> value.format(depth + 1));
        Iter<String> nodeListsStream = MapNode.toStream(depth, this.nodeLists, (NodeList values) -> "[" + MapNode.formatNodeList(depth, values) + "]");

        String joined = stringsStream.concat(nodesStream)
                .concat(nodeListsStream)
                .collect(new Joiner(", "))
                .orElse("");

        return typeString + "{" + joined + MapNode.createIndent(depth) + "}";
    }

    @Override
    public Node withString(String key, String value) {
        this.strings = this.strings.put(key, value);
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
        return this.maybeType.filter((String inner) -> inner.equals(type)).isPresent();
    }

    @Override
    public Node retype(String type) {
        return new MapNode(new Some<String>(type), this.strings, this.nodes, this.nodeLists);
    }

    @Override
    public Node withNode(String key, Node value) {
        this.nodes = this.nodes.put(key, value);
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
        var withStrings = MapNode.fold(this, other.iterStrings(), (Node current) -> (String key2, String value1) -> current.withString(key2, value1));
        var withNodes = MapNode.fold(withStrings, other.iterNodes(), (Node current) -> (String key1, Node value) -> current.withNode(key1, value));
        return MapNode.fold(withNodes, other.iterNodeLists(), (Node current) -> (String key, NodeList values) -> current.withNodeList(key, values));
    }

    @Override
    public Iter<Tuple2<String, String>> iterStrings() {
        return this.strings().iter();
    }

    @Override
    public boolean hasNodeList(String key) {
        return this.nodeLists.containsKey(key);
    }

    @Override
    public EmptyDestroyer destroy() {
        return new EmptyDestroyer(this);
    }

    @Override
    public CompileResult<Tuple2<Node, NodeList>> removeNodeList(String key) {
        this.nodeLists.removeKey(key);
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.strings.isEmpty() && this.nodes.isEmpty() && this.nodeLists.isEmpty();
    }

    @Override
    public <T> Node withNodeListFromElements(String key, List<T> list, Function<T, Node> serializer) {
        NodeList nodeList = list.iter()
                .map(serializer)
                .collect(new NodeListCollector());

        return this.withNodeList(key, nodeList);
    }

    @Override
    public Iter<Tuple2<String, NodeList>> iterNodeLists() {
        return this.nodeLists.iter();
    }

    @Override
    public Node withNodeList(String key, NodeList values) {
        this.nodeLists = this.nodeLists.put(key, values);
        return this;
    }

    @Override
    public String toString() {
        return this.display();
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

    @Override
    public CompileResult<Node> findNodeOrError(String key) {
        return this.findNode(key)
                .map((Node node1) -> CompileResults.fromResult(new Ok<>(node1)))
                .orElseGet(() -> CompileErrors.createNodeError("Node '" + key + "' not present", this));
    }

    @Override
    public CompileResult<NodeList> findNodeListOrError(String key) {
        return this.findNodeList(key)
                .map((NodeList list) -> CompileResults.fromOk(list))
                .orElseGet(() -> CompileErrors.createNodeError("Node list '" + key + "' not present", this));
    }
}