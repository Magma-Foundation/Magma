package magma.app.compile;

import jvm.api.collect.Maps;
import magma.api.collect.Map_;
import magma.api.collect.Stream;
import magma.api.option.Option;
import magma.api.result.Tuple;

public record MapNode(Map_<String, String> strings) implements Node {
    public MapNode() {
        this(Maps.empty());
    }

    @Override
    public Node withString(String propertyKey, String propertyValue) {
        return new MapNode(strings.put(propertyKey, propertyValue));
    }

    @Override
    public Option<String> findString(String propertyKey) {
        return strings.get(propertyKey);
    }

    @Override
    public Node merge(Node other) {
        return other.streamStrings().<Node>foldWithInitial(this, (node, tuple) -> node.withString(tuple.left(), tuple.right()));
    }

    @Override
    public String display() {
        return strings.toString();
    }

    @Override
    public Stream<Tuple<String, String>> streamStrings() {
        return strings.stream();
    }
}