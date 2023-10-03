package com.meti.compile.node;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.collect.map.ImmutableMaps;
import com.meti.api.collect.map.Map;
import com.meti.api.option.Option;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodeListAttribute;
import com.meti.compile.attribute.StringAttribute;

public record MapNode(JavaString name, Map<JavaString, Attribute> attributes) implements Node {
    public static Builder Builder(JavaString name) {
        return new Builder(name);
    }

    public static Builder Builder(String name) {
        return new Builder(JavaString.apply(name));
    }

    @Override
    public JavaString toXML() {
        var actualName = name.value();
        var joinedAttributes = attributes.iter()
                .map(entry -> entry.a()
                        .prepend(" ")
                        .append("=")
                        .append(entry.b().toXML()))
                .collect(Collectors.joining())
                .unwrapOrElse(JavaString.Empty);

        return new JavaString("<" + actualName + joinedAttributes.value() + "></" + actualName + ">");
    }

    @Override
    public boolean is(String name) {
        return this.name.equalsToSlice(name);
    }

    @Override
    public Option<Attribute> apply(JavaString name) {
        return attributes.get(name);
    }

    public record Builder(JavaString name, Map<JavaString, Attribute> attributes) {
        public Builder(JavaString name) {
            this(name, ImmutableMaps.empty());
        }

        public Node complete() {
            return new MapNode(name, attributes);
        }

        public Builder withNodeList(JavaString name, List<? extends Node> value) {
            return new Builder(this.name, attributes.put(name, new NodeListAttribute(value)));
        }

        public Builder withString(JavaString name, JavaString value) {
            return new Builder(this.name, attributes.put(name, new StringAttribute(value)));
        }

        public Builder withNode(String name, Node value) {
            return new Builder(this.name, attributes.put(new JavaString(name), new NodeAttribute(value)));
        }

        public Builder withString(String name, JavaString value) {
            return new Builder(this.name, attributes.put(new JavaString(name), new StringAttribute(value)));
        }

        public Builder withNodeList(String name, List<? extends Node> values) {
            return withNodeList(JavaString.apply(name), values);
        }
    }
}
