package magma.compile;

import jvm.collect.list.Lists;
import jvm.collect.map.Maps;
import jvm.collect.stream.Streams;
import magma.collect.list.List_;
import magma.collect.map.MapCollector;
import magma.collect.map.Map_;
import magma.collect.stream.Joiner;
import magma.collect.stream.Stream;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;

import java.util.function.Function;

public final class MapNode implements Node {
    private final Option<String> maybeType;
    private final Map_<String, String> strings;
    private final Map_<String, Node> nodes;
    private final Map_<String, List_<Node>> nodeLists;

    public MapNode() {
        this(new None<>(), Maps.empty(), Maps.empty(), Maps.empty());
    }

    public MapNode(Option<String> maybeType, Map_<String, String> strings, Map_<String, Node> nodes, Map_<String, List_<Node>> nodeLists) {
        this.maybeType = maybeType;
        this.strings = strings;
        this.nodes = nodes;
        this.nodeLists = nodeLists;
    }

    public MapNode(String type) {
        this(new Some<>(type), Maps.empty(), Maps.empty(), Maps.empty());
    }

    private static String formatEntry(int depth, String key, String value) {
        String format = "%s%s: %s";
        String indent = "\t".repeat(depth + 1);
        return format.formatted(indent, key, value);
    }

    @Override
    public Node withString(String propertyKey, String propertyValue) {
        return new MapNode(maybeType, strings.with(propertyKey, propertyValue), nodes, nodeLists);
    }

    @Override
    public Option<String> findString(String propertyKey) {
        return strings.find(propertyKey);
    }

    @Override
    public Node withNodeList(String propertyKey, List_<Node> propertyValues) {
        return new MapNode(maybeType, strings, nodes, nodeLists.with(propertyKey, propertyValues));
    }

    @Override
    public Option<List_<Node>> findNodeList(String propertyKey) {
        return nodeLists.find(propertyKey);
    }

    @Override
    public String display() {
        return format(0);
    }

    @Override
    public String format(int depth) {
        String typeString = maybeType.map(type -> type + " ").orElse("");

        Option<String> joinedStrings = strings.stream()
                .map(entry -> formatEntry(depth, entry.left(), "\"" + entry.right() + "\""))
                .collect(new Joiner(",\n"));

        Option<String> joinedNodes = nodes.stream()
                .map(entry -> formatEntry(depth, entry.left(), entry.right().format(depth + 1)))
                .collect(new Joiner(",\n"));

        Option<String> joinedNodeLists = nodeLists.stream()
                .map(entry -> formatEntry(depth, entry.left(), formatList(entry, depth)))
                .collect(new Joiner(",\n"));

        String joined = Streams.of(joinedStrings, joinedNodes, joinedNodeLists)
                .flatMap(Streams::fromOption)
                .collect(new Joiner(",\n"))
                .orElse("");

        return typeString + "{\n" + joined + "\n" +
                "\t".repeat(depth) +
                "}";
    }

    private String formatList(Tuple<String, List_<Node>> entry, int depth) {
        return "[" + entry.right()
                .stream()
                .map(node -> node.format(depth + 1))
                .collect(new Joiner(", "))
                .orElse("") + "]";
    }

    @Override
    public Node mapNodeList(String propertyKey, Function<List_<Node>, List_<Node>> mapper) {
        return findNodeList(propertyKey)
                .map(mapper)
                .map(nodeList -> withNodeList(propertyKey, nodeList))
                .orElse(this);
    }

    @Override
    public boolean is(String type) {
        return this.maybeType.filter(value -> value.equals(type)).isPresent();
    }

    @Override
    public Node retype(String type) {
        return new MapNode(new Some<>(type), strings, nodes, nodeLists);
    }

    @Override
    public Node merge(Node other) {
        Node withStrings = other.streamStrings().<Node>foldWithInitial(this, (node, tuple) -> node.withString(tuple.left(), tuple.right()));
        Node withNodes = other.streamNodes().foldWithInitial(withStrings, (node, tuple) -> node.withNode(tuple.left(), tuple.right()));
        return other.streamNodeLists().foldWithInitial(withNodes, (node, tuple) -> node.withNodeList(tuple.left(), tuple.right()));
    }

    @Override
    public Stream<Tuple<String, String>> streamStrings() {
        return strings.stream();
    }

    @Override
    public Stream<Tuple<String, List_<Node>>> streamNodeLists() {
        return nodeLists.stream();
    }

    @Override
    public Node withNode(String propertyKey, Node propertyValue) {
        return new MapNode(maybeType, strings, nodes.with(propertyKey, propertyValue), nodeLists);
    }

    @Override
    public Option<Node> findNode(String propertyKey) {
        return nodes.find(propertyKey);
    }

    @Override
    public Stream<Tuple<String, Node>> streamNodes() {
        return nodes.stream();
    }

    @Override
    public Node mapNode(String propertyKey, Function<Node, Node> mapper) {
        return findNode(propertyKey).map(mapper).map(node -> withNode(propertyKey, node)).orElse(this);
    }

    @Override
    public Node withNodeLists(Map_<String, List_<Node>> nodeLists) {
        return new MapNode(maybeType, strings, nodes, this.nodeLists.withAll(nodeLists));
    }

    @Override
    public Node withNodes(Map_<String, Node> nodes) {
        return new MapNode(maybeType, strings, this.nodes.withAll(nodes), this.nodeLists);
    }

    @Override
    public Node removeNode(String propertyKey) {
        return new MapNode(maybeType, strings, nodes.remove(propertyKey), nodeLists);
    }

    @Override
    public boolean hasNode(String propertyKey) {
        return nodes.containsKey(propertyKey);
    }

    @Override
    public boolean hasNodeList(String propertyKey) {
        return nodeLists.containsKey(propertyKey);
    }

    @Override
    public Boolean equalsTo(Node other) {
        boolean isType = maybeType.map(other::is).orElse(false);

        Map_<String, String> stringsCopy = other.streamStrings().collect(new MapCollector<>());
        Map_<String, Node> nodesCopy = other.streamNodes().collect(new MapCollector<>());
        Map_<String, List_<Node>> nodeListCopy = other.streamNodeLists().collect(new MapCollector<>());

        return isType && Maps.equalsTo(strings, stringsCopy, String::equals)
                && Maps.equalsTo(nodes, nodesCopy, Node::equalsTo)
                && Maps.equalsTo(nodeLists, nodeListCopy, this::doNodeListsEqual);
    }

    private boolean doNodeListsEqual(List_<Node> nodeList, List_<Node> nodeList2) {
        return Lists.equalsTo(nodeList, nodeList2, Node::equalsTo);
    }

    @Override
    public String toString() {
        return display();
    }
}