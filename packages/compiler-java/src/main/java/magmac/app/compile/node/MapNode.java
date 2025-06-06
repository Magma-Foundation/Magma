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
import magmac.app.compile.error.type.CompileErrors;

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
            var left = tuple.left();
            var right = tuple.right();

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
        var indent = MapNode.createIndent(depth + 1);
        return indent + key + ": " + value;
    }

    private static <T> Iter<String> toStream(int depth, Map<String, T> map, Function<T, String> mapper) {
        if (map.isEmpty()) {
            return Iters.empty();
        }

        return map.iter().map((Tuple2<String, T> entry) -> {
            var key = entry.left();
            var value = entry.right();
            return MapNode.formatEntry(depth, key, mapper.apply(value));
        });
    }

    @Override
    public <T> Node withNodeAndSerializer(String key, T element, Function<T, Node> serializer) {
        return this.withNode(key, serializer.apply(element));
    }

    @Override
    public Option<Tuple2<Node, Node>> removeNode(String key) {
        return this.nodes.removeByKey(key).map(tuple -> {
            var copy = new MapNode(this.maybeType, this.strings, tuple.left(), this.nodeLists);
            return new Tuple2<>(copy, tuple.right());
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
        var typeString = this.maybeType
                .map((String type) -> type + " ")
                .orElse("");

        var stringsStream = MapNode.toStream(depth, this.strings, (String value) -> "\"" + value + "\"");
        var nodesStream = MapNode.toStream(depth, this.nodes, (Node value) -> value.format(depth + 1));
        var nodeListsStream = MapNode.toStream(depth, this.nodeLists, (NodeList values) -> "[" + MapNode.formatNodeList(depth, values) + "]");

        var joined = stringsStream.concat(nodesStream)
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
        var withStrings = MapNode.fold(this, other.iterStrings(), (Node current) -> current::withString);
        var withNodes = MapNode.fold(withStrings, other.iterNodes(), (Node current) -> current::withNode);
        return MapNode.fold(withNodes, other.iterNodeLists(), (Node current) -> current::withNodeList);
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
    public CompileResult<Tuple2<Node, NodeList>> removeNodeListOrError(String key) {
        return this.removeNodeList(key).map(CompileResults::Ok).orElseGet(() -> this.createNotPresent(key));
    }

    @Override
    public Option<Tuple2<Node, NodeList>> removeNodeList(String key) {
        return this.nodeLists.removeByKey(key)
                .map(tuple -> new Tuple2<>(this.withNodeLists(tuple.left()), tuple.right()));
    }

    private Node withNodeLists(Map<String, NodeList> nodeLists) {
        return new MapNode(this.maybeType, this.strings, this.nodes, nodeLists);
    }

    @Override
    public boolean hasContent() {
        return !this.strings.isEmpty() || !this.nodes.isEmpty() || !this.nodeLists.isEmpty();
    }

    @Override
    public <T> Node withNodeListAndSerializer(String key, List<T> list, Function<T, Node> serializer) {
        var nodeList = list.iter()
                .map(serializer)
                .collect(new NodeListCollector());

        return this.withNodeList(key, nodeList);
    }

    @Override
    public CompileResult<Tuple2<Node, String>> removeString(String key) {
        return this.strings.removeByKey(key)
                .map((Tuple2<Map<String, String>, String> tuple) -> CompileResults.Ok(new Tuple2<>(this.withStrings(tuple.left()), tuple.right())))
                .orElseGet(() -> this.createNotPresent(key));
    }

    @Override
    public CompileResult<Tuple2<Node, Node>> removeNodeOrError(String key) {
        return this.nodes.removeByKey(key)
                .map((Tuple2<Map<String, Node>, Node> tuple) -> CompileResults.Ok(new Tuple2<>(this.withNodes(tuple.left()), tuple.right())))
                .orElseGet(() -> this.createNotPresent(key));
    }

    private <T> CompileResult<Tuple2<Node, T>> createNotPresent(String key) {
        return CompileResults.NodeErr("Key '" + key + "' not present", this);
    }

    private Node withNodes(Map<String, Node> nodes) {
        return new MapNode(this.maybeType, this.strings, nodes, this.nodeLists);
    }

    private Node withStrings(Map<String, String> strings) {
        return new MapNode(this.maybeType, strings, this.nodes, this.nodeLists);
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
                .map(CompileResults::Ok)
                .orElseGet(() -> CompileErrors.createNodeError("Node list '" + key + "' not present", this));
    }
}