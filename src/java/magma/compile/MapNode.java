package magma.compile;

import jvm.collect.map.Maps;
import magma.collect.list.List_;
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
    private final Map_<String, List_<Node>> nodeLists;

    public MapNode() {
        this(new None<>(), Maps.empty(), Maps.empty());
    }

    public MapNode(Option<String> maybeType, Map_<String, String> strings, Map_<String, List_<Node>> nodeLists) {
        this.maybeType = maybeType;
        this.strings = strings;
        this.nodeLists = nodeLists;
    }

    @Override
    public Node withString(String propertyKey, String propertyValue) {
        return new MapNode(maybeType, strings.with(propertyKey, propertyValue), nodeLists);
    }

    @Override
    public Option<String> findString(String propertyKey) {
        return strings.find(propertyKey);
    }

    @Override
    public Node withNodeList(String propertyKey, List_<Node> propertyValues) {
        return new MapNode(maybeType, strings, nodeLists.with(propertyKey, propertyValues));
    }

    @Override
    public Option<List_<Node>> findNodeList(String propertyKey) {
        return nodeLists.find(propertyKey);
    }

    @Override
    public String display() {
        String typeString = maybeType.map(type -> type + " ").orElse("");

        String joinedStrings = strings.stream()
                .map(entry -> "\t" + entry.left() + ": \"" + entry.right() + "\"")
                .collect(new Joiner(",\n"))
                .orElse("");

        String joinedNodeLists = nodeLists.stream()
                .map(entry -> entry.left() + ": [" + entry.right()
                        .stream()
                        .map(Node::display)
                        .collect(new Joiner(","))
                        .orElse("") + "]")
                .collect(new Joiner(",\n"))
                .orElse("");

        return typeString + "{\n" + joinedStrings + joinedNodeLists + "}";
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
        return new MapNode(new Some<>(type), strings, nodeLists);
    }

    @Override
    public Node merge(Node other) {
        Node withStrings = other.streamStrings().<Node>foldWithInitial(this, (node, tuple) -> node.withString(tuple.left(), tuple.right()));
        return other.streamNodeLists().foldWithInitial(withStrings, (node, tuple) -> node.withNodeList(tuple.left(), tuple.right()));
    }

    @Override
    public Stream<Tuple<String, String>> streamStrings() {
        return strings.stream();
    }

    @Override
    public Stream<Tuple<String, List_<Node>>> streamNodeLists() {
        return nodeLists.stream();
    }
}