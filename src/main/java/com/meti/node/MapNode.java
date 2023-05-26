package com.meti.node;

import java.util.*;
import java.util.stream.Stream;

public class MapNode implements Node {

    private final String identifier;
    private final Map<String, Attribute> attributes;
    private final Map<Group, List<Object>> groups;

    public MapNode(String identifier, Map<String, Attribute> attributes) {
        this(identifier, attributes, Collections.emptyMap());
    }

    public MapNode(String identifier, Map<String, Attribute> attributes, Map<Group, List<Object>> groups) {
        this.identifier = identifier;
        this.attributes = new HashMap<>(attributes);
        this.groups = new HashMap<>(groups);
    }

    public MapNode(String identifier) {
        this(identifier, Collections.emptyMap(), new HashMap<>());
    }

    @Override
    public String toString() {
        return "MapNode{" +
               "identifier='" + identifier + '\'' +
               ", attributes=" + attributes +
               '}';
    }

    @Override
    public boolean is(Object key) {
        return identifier.equals(key);
    }

    @Override
    public Stream<Object> stream(Group group) {
        if (groups.containsKey(group)) {
            return groups.get(group).stream();
        } else {
            return Stream.empty();
        }
    }

    @Override
    public Optional<Node> with(Object key, Attribute value) {
        if (key instanceof String && attributes.containsKey(key)) {
            var copy = new HashMap<>(attributes);
            copy.put((String) key, value);
            return Optional.of(new MapNode(identifier, copy, new HashMap<Group, List<Object>>()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if (key instanceof String && attributes.containsKey(key)) {
            return Optional.of(attributes.get(key));
        }

        return Optional.empty();
    }
}
