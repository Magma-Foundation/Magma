package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record MapNode(JavaString name, Map<JavaString, Attribute> attributes) implements Node {
    public static Builder Builder(JavaString name) {
        return new Builder(name);
    }

    @Override
    public boolean is(String name) {
        return this.name.equalsToSlice(name);
    }

    @Override
    public Option<Attribute> apply(JavaString name) {
        return attributes.get(name);
    }

    @Override
    public Option<Node> with(JavaString name, Attribute attribute) {
        if (attributes.hasKey(name)) {
            return Some.apply(new MapNode(this.name, attributes.put(name, attribute)));
        } else {
            return None.apply();
        }
    }

    public record Builder(JavaString name, Map<JavaString, Attribute> attributes) {
        public Builder(JavaString name) {
            this(name, ImmutableMaps.empty());
        }

        public Node complete() {
            return new MapNode(name, attributes);
        }

        public Builder withListOfNodes(JavaString name, List<? extends Node> value) {
            return new Builder(this.name, attributes.put(name, new NodeListAttribute(value)));
        }

        public Builder withString(JavaString name, JavaString value) {
            return new Builder(this.name, attributes.put(name, new StringAttribute(value)));
        }

        public Builder withNode(String name, Node value) {
            return new Builder(this.name, attributes.put(new JavaString(name), new NodeAttribute(value)));
        }
    }
}
