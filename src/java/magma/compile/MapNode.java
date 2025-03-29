package magma.compile;

import jvm.collect.map.Maps;
import magma.collect.map.Map_;
import magma.option.Option;

public final class MapNode implements Node {
    private final Map_<String, String> strings;

    public MapNode() {
        this(Maps.empty());
    }

    public MapNode(Map_<String, String> strings) {
        this.strings = strings;
    }

    @Override
    public Node withString(String propertyKey, String propertyValue) {
        return new MapNode(strings.with(propertyKey, propertyValue));
    }

    @Override
    public Option<String> findString(String propertyKey) {
        return strings.find(propertyKey);
    }
}