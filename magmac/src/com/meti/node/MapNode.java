package com.meti.node;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapNode implements Node {
    private final Map<String, Attribute> attributes;
    private final String name;

    public MapNode(String name, Map<String, Attribute> attributes) {
        this.attributes = attributes;
        this.name = name;
    }

    private static AbstractMap.SimpleImmutableEntry<String, Optional<Attribute>> applyMapperToNodePair(Map.Entry<String, Attribute> pair, Function<Node, Optional<Node>> mapper) {
        return applyMapperToPair(pair, Attribute::asNode, mapper, NodeAttribute::new);
    }

    private static <T> AbstractMap.SimpleImmutableEntry<String, Optional<Attribute>> applyMapperToPair(
            Map.Entry<String, Attribute> pair,
            Function<Attribute, Optional<T>> as,
            Function<T, Optional<T>> mapper,
            Function<T, Attribute> constructor) {

        var attribute = pair.getValue();
        var nodeOptional = as.apply(attribute)
                .flatMap(mapper)
                .map(constructor)
                .or(() -> Optional.of(attribute));

        return new AbstractMap.SimpleImmutableEntry<>(pair.getKey(), nodeOptional);
    }

    private static AbstractMap.SimpleImmutableEntry<String, Optional<Attribute>> applyMapperToNodeListPair(
            Map.Entry<String, Attribute> pair,
            Function<List<Node>, Optional<List<Node>>> mapper) {
        return applyMapperToPair(pair, Attribute::asListOfNodes, mapper, NodeListAttribute::new);
    }

    @Override
    public Node replace(String name, Attribute attribute) {
        var copy = new HashMap<>(attributes);
        copy.put(name, attribute);
        return new MapNode(this.name, copy);
    }

    @Override
    public Node map(String name, Function<Attribute, Attribute> mapper) {
        if (!attributes.containsKey(name)) return this;

        var newAttribute = mapper.apply(attributes.get(name));
        return replace(name, newAttribute);
    }

    @Override
    public Node ensure(String name, Attribute attribute) {
        if (attributes.containsKey(name)) return this;
        else return replace(name, attribute);
    }

    @Override
    public Optional<Attribute> apply(String name) {
        return attributes.containsKey(name) ? Optional.of(attributes.get(name)) : Optional.empty();
    }

    @Override
    public Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        return mapAttributes(pair -> applyMapperToNodePair(pair, mapper));
    }

    private Optional<Node> mapAttributes(Function<Map.Entry<String, Attribute>, Map.Entry<String, Optional<Attribute>>> mapper) {
        var map = attributes.entrySet()
                .stream()
                .map(mapper)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (map.values().stream().allMatch(Optional::isPresent)) {
            var newEntries = map.entrySet()
                    .stream()
                    .map(pair -> pair.getValue().map(value -> new AbstractMap.SimpleImmutableEntry<>(pair.getKey(), value)))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));

            return Optional.of(new MapNode(name, newEntries));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean is(String id) {
        return this.name.equals(id);
    }

    @Override
    public String toString() {
        return "MapNode{" +
               "name='" + name + '\'' + ", " +
               "attributes=" + attributes +
               '}';
    }

    @Override
    public Optional<Node> mapNodeLists(Function<List<Node>, Optional<List<Node>>> mapper) {
        return mapAttributes(pair -> applyMapperToNodeListPair(pair, mapper));
    }
}
