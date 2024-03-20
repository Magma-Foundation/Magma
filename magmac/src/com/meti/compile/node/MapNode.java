package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Pair;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

import java.util.HashMap;

public record MapNode(JavaString name, JavaMap<JavaString, Attribute> attributes) implements Node {
    public static Builder Builder(JavaString name) {
        return new Builder(name);
    }

    @Override
    public boolean is(String name) {
        return this.name.equalsToSlice(name);
    }

    @Override
    public Option<Attribute> apply(String name) {
        return attributes.apply(JavaString.from(name));
    }

    @Override
    public Option<Node> with(String name, Attribute attribute) {
        var ownedName = JavaString.from(name);
        return attributes.replaceValue(ownedName, attribute).map(newAttributes -> new MapNode(this.name, newAttributes));
    }

    @Override
    public Stream<Pair<JavaString, Attribute>> streamPairs(String type) {
        return attributes.stream().filter(tuple -> tuple.applyRight(attribute -> attribute.is(type)));
    }

    public record Builder(JavaString name, JavaMap<JavaString, Attribute> attributes) {
        public Builder(JavaString name) {
            this(name, new JavaMap<>(new HashMap<>()));
        }

        public Builder withListOfStrings(String name, JavaList<JavaString> values) {
            return new Builder(this.name, attributes.put(JavaString.from(name), new StringListAttribute(values)));
        }

        public Node complete() {
            return new MapNode(name, attributes);
        }

        public Builder withString(String name, JavaString value) {
            return new Builder(this.name, attributes.put(JavaString.from(name), new StringAttribute(value)));
        }

        public Builder withNode(String name, Node value) {
            return new Builder(this.name, attributes.put(JavaString.from(name), new NodeAttribute(value)));
        }

        public Builder withListOfNodes(String name, JavaList<? extends Node> values) {
            return new Builder(this.name, attributes.put(JavaString.from(name), new NodeListAttribute(values)));
        }

        public Builder withInteger(String name, int value) {
            return new Builder(this.name, attributes.put(JavaString.from(name), new IntegerAttribute(value)));
        }
    }
}
