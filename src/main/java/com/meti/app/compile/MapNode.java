package com.meti.app.compile;

import com.meti.app.compile.attribute.*;
import com.meti.app.compile.clazz.Extractor;
import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.iterate.Iterator;
import com.meti.java.*;

public record MapNode(String_ name1, Map<String_, Attribute> attributes) implements Node {
    public MapNode(String_ name) {
        this(name, JavaMap.empty());
    }

    public static Builder create(String_ name) {
        return new Builder(name);
    }

    public static Builder create(String slice) {
        return new Builder(JavaString.fromSlice(slice));
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
               "name: '" + name1.unwrap() + "', " +
               "attributes: " + attributes.toString() +
               "}";
    }

    @Override
    public Node with(Key<String_> key, Attribute attribute) {
        return key.peek(keyValue -> new MapNode(name1, attributes.insert(keyValue, attribute)));
    }

    @Override
    public boolean is(String_ name) {
        return this.name1.equalsTo(name);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Option<Map<String_, Attribute>> extract(Node format) {
        throw new UnsupportedOperationException();
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

        public Builder with(Extractor.Extraction extraction) {
            return copy(this.attributes.insert(extraction.name(), extraction.attribute()));
        }

        public Builder withSetOfStrings(String name, Set<String_> values) {
            return copy(this.attributes.insert(JavaString.fromSlice(name), new StringSetAttribute(values)));
        }

        public Builder withString(String name, String_ name1) {
            return withAttribute(name, new StringAttribute(name1));
        }

        private Builder withAttribute(String name, Attribute attribute) {
            return copy(this.attributes.insert(JavaString.fromSlice(name), attribute));
        }

        private Builder copy(Map<String_, Attribute> attributes) {
            return new Builder(this.name, attributes);
        }

        public Builder withListOfNodes(String name, String type, List<Node> values) {
            return withAttribute(name, new NodeListAttribute(JavaString.fromSlice(type), values));
        }
    }
}
