package com.meti.app.compile;

import com.meti.app.compile.attribute.*;
import com.meti.app.compile.clazz.Extractions;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.iterate.Collectors;
import com.meti.iterate.Iterator;
import com.meti.java.*;

import static com.meti.app.compile.attribute.ExtractAttribute.Extract;

public record MapNode(String_ type, Map<String_, Attribute> attributes) implements Node {
    public MapNode(String_ name) {
        this(name, JavaMap.empty());
    }

    public static Builder create(String_ name) {
        return new Builder(name);
    }

    public static Builder create(String slice) {
        return new Builder(JavaString.fromSlice(slice));
    }

    private static Option<Map<String_, Attribute>> extractAttribute(String_ key, Attribute thisAttribute, Attribute otherAttribute) {
        if (otherAttribute.equalsTo(Extract)) {
            return Some.apply(JavaMap.<String_, Attribute>empty().insert(key, thisAttribute));
        } else {
            return extractNodeAttribute(thisAttribute, otherAttribute).or(extractNodeListAttribute(thisAttribute, otherAttribute));
        }
    }

    private static Option<Map<String_, Attribute>> extractNodeListAttribute(Attribute thisAttribute, Attribute otherAttribute) {
        return thisAttribute.asListOfNodes().and(otherAttribute.asListOfNodes()).flatMap(listTuple -> {
            var thisList = listTuple.a().b();
            var formatList = listTuple.b().b();

            return thisList.iter().zip(formatList.iter()).map(innerNodeTuple -> {
                var thisNode = innerNodeTuple.a();
                var formatNode = innerNodeTuple.b();
                return thisNode.extract(formatNode);
            }).collect(Collectors.andRequireAll(JavaMap.toIntersection()));
        });
    }

    private static Option<Map<String_, Attribute>> extractNodeAttribute(Attribute thisAttribute, Attribute otherAttribute) {
        return thisAttribute.asNode().and(otherAttribute.asNode()).flatMap(nodeTuple -> {
            var thisNode = nodeTuple.a().b();
            var formatNode = nodeTuple.b().b();
            return thisNode.extract(formatNode);
        });
    }

    @Override
    public Option<Attribute> applyOptionally(String_ key) {
        return attributes.applyOptionally(key);
    }

    @Override
    public Option<Key<String_>> has(String_ key) {
        return attributes.applyOptionally(key).replace(new ImmutableKey<>(key));
    }

    @Override
    public String toString() {
        return "{" +
               "name: '" + type.unwrap() + "', " +
               "attributes: " + attributes.toString() +
               "}";
    }

    @Override
    public Node with(Key<String_> key, Attribute attribute) {
        return key.peek(keyValue -> new MapNode(type, attributes.insert(keyValue, attribute)));
    }

    @Override
    public boolean is(String_ name) {
        return this.type.equalsTo(name);
    }

    @Override
    public Attribute apply(Key<String_> key) {
        return attributes.apply(key);
    }

    @Override
    public Iterator<Key<String_>> ofGroup(Group group) {
        return attributes.iter()
                .filter(tuple -> tuple.b().is(group))
                .map(Tuple::a)
                .map(ImmutableKey::new);
    }

    @Override
    public String_ getType() {
        return this.type;
    }

    @Override
    public boolean equalsTo(Node other) {
        return other.is(type) && this.attributes.iter().allMatch(entry ->
                other.applyOptionally(entry.a()).map(otherAttribute ->
                        entry.b().equalsTo(otherAttribute)).unwrapOrElse(false));
    }

    @Override
    public Iterator<Tuple<Key<String_>, Attribute>> entries() {
        return this.attributes.iter().map(tuple -> tuple.mapLeft(ImmutableKey::new));
    }

    @Override
    public Option<Map<String_, Attribute>> extract(Node format) {
        return keys().then(format.keys()).distinct()
                .map(Key::unwrap)
                .map(key -> applyOptionally(key)
                        .and(format.applyOptionally(key))
                        .flatMap(attributeTuple -> extractAttribute(key, attributeTuple.a(), attributeTuple.b())))
                .collect(Collectors.andRequireAll(JavaMap.toIntersection()));
    }

    @Override
    public Iterator<Key<String_>> keys() {
        return attributes().keys();
    }

    public record Builder(String_ name, Map<String_, Attribute> attributes) {
        public Builder(String_ name) {
            this(name, JavaMap.empty());
        }

        public Builder withString(String_ name, String_ value) {
            return copy(attributes.insert(name, new StringAttribute(value)));
        }

        public Builder withNode(String_ name, String_ type, Node value) {
            return copy(attributes.insert(name, new NodeAttribute(type, value)));
        }

        public Node complete() {
            return new MapNode(this.name, this.attributes);
        }

        public Builder withNode(String name, String type, Builder builder) {
            return copy(this.attributes.insert(JavaString.fromSlice(name), new NodeAttribute(JavaString.fromSlice(type), builder.complete())));
        }

        public Builder with(Extractions.Extraction extraction) {
            return copy(this.attributes.insert(extraction.name(), extraction.attribute()));
        }

        public Builder withSetOfStrings(String name, Set<String_> values) {
            return copy(this.attributes.insert(JavaString.fromSlice(name), new StringSetAttribute(values)));
        }

        public Builder withString(String name, String_ name1) {
            return withAttribute(name, new StringAttribute(name1));
        }

        public Builder withAttribute(String name, Attribute attribute) {
            return copy(this.attributes.insert(JavaString.fromSlice(name), attribute));
        }

        private Builder copy(Map<String_, Attribute> attributes) {
            return new Builder(this.name, attributes);
        }

        public Builder withListOfNodes(String name, String type, List<Node> values) {
            return withAttribute(name, new NodeListAttribute(JavaString.fromSlice(type), values));
        }

        public Builder withString(String name, String value) {
            return withAttribute(name, new StringAttribute(JavaString.fromSlice(value)));
        }
    }
}
