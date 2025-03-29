package magma.compile;

import jvm.collect.map.Maps;
import magma.collect.list.List_;
import magma.collect.map.Map_;
import magma.option.None;
import magma.option.Option;

import java.util.function.Function;

public final class MapNode implements Node {
    private final Option<String> type;
    private final Map_<String, String> strings;
    private final Map_<String, List_<Node>> nodeLists;

    public MapNode() {
        this(new None<>(), Maps.empty(), Maps.empty());
    }

    public MapNode(Option<String> type, Map_<String, String> strings, Map_<String, List_<Node>> nodeLists) {
        this.type = type;
        this.strings = strings;
        this.nodeLists = nodeLists;
    }

    @Override
    public Node withString(String propertyKey, String propertyValue) {
        return new MapNode(type, strings.with(propertyKey, propertyValue), nodeLists);
    }

    @Override
    public Option<String> findString(String propertyKey) {
        return strings.find(propertyKey);
    }

    @Override
    public Node withNodeList(String propertyKey, List_<Node> propertyValues) {
        return new MapNode(type, strings, nodeLists.with(propertyKey, propertyValues));
    }

    @Override
    public Option<List_<Node>> findNodeList(String propertyKey) {
        return nodeLists.find(propertyKey);
    }

    @Override
    public String display() {
        return "";
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
        return this.type.filter(value -> value.equals(type)).isPresent();
    }
}