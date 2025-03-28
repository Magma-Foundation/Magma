package magma;

import magma.api.collect.Map_;
import jvm.api.collect.Maps;
import magma.api.option.Option;

public record MapNode(Map_<String, String> strings) {
    public MapNode() {
        this(Maps.empty());
    }

    public MapNode withString(String propertyKey, String propertyValue) {
        return new MapNode(strings.put(propertyKey, propertyValue));
    }

    public Option<String> find(String propertyKey) {
        return strings.get(propertyKey);
    }

    public MapNode merge(MapNode other) {
        return new MapNode(strings.putAll(other.strings));
    }
}