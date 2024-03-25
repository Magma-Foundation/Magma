package com.meti.node;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapNode implements Node {
    private final Map<String, Attribute> attributes;

    public MapNode(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    private static AbstractMap.SimpleImmutableEntry<String, Optional<Attribute>> applyMapperToPair(Map.Entry<String, Attribute> pair, Function<Node, Optional<Node>> mapper) {
        var attribute = pair.getValue();
        var nodeOptional = attribute.asNode();
        if (nodeOptional.isPresent()) {
            var node = nodeOptional.get();
            var optional = mapper.apply(node).<Attribute>map(NodeAttribute::new);
            return new AbstractMap.SimpleImmutableEntry<>(pair.getKey(), optional);
        } else {
            return new AbstractMap.SimpleImmutableEntry<>(pair.getKey(), Optional.of(attribute));
        }
    }

    @Override
    public Optional<Attribute> apply(String name) {
        if (attributes.containsKey(name)) return Optional.of(attributes.get(name));
        else return Optional.empty();
    }

    @Override
    public Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        var map = attributes.entrySet()
                .stream()
                .<Map.Entry<String, Optional<Attribute>>>map(pair -> applyMapperToPair(pair, mapper))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (map.values().stream().allMatch(Optional::isPresent)) {
            var newEntries = map.entrySet()
                    .stream()
                    .map(pair -> pair.getValue().map(value -> new AbstractMap.SimpleImmutableEntry<>(pair.getKey(), value)))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

            return Optional.of(new MapNode(newEntries));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Node> mapNodeLists(Function<List<Node>, Optional<List<Node>>> mapper) {
        return Node.super.mapNodeLists(mapper);
    }
}
