package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.StringAttribute;
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

    public record Builder(String_ name, Map<String_, Attribute> attributes) {
        public Builder(String_ name) {
            this(name, JavaMap.empty());
        }

        public Builder withString(String_ name, String_ value) {
            return new Builder(this.name, attributes.insert(name, new StringAttribute(value)));
        }

        public Builder withNode(String_ name, String_ type, Node value) {
            return new Builder(this.name, attributes.insert(name, new NodeAttribute(type, value)));
        }

        public Node complete() {
            return new MapNode(this.name, this.attributes);
        }
    }
}
