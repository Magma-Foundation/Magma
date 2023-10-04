package com.meti.compile.attribute;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.node.Node;

public record NodeListAttribute(List<? extends Node> values) implements Attribute {
    public static Attribute from(List<? extends Node> values) {
        return new NodeListAttribute(values);
    }

    @Override
    public boolean is(Group group) {
        return group == Group.NodeList;
    }

    @Override
    public Option<List<? extends Node>> asListOfNodes() {
        return Some.apply(values);
    }

    @Override
    public JavaString toXML() {
        return values.iter()
                .map(Node::toXML)
                .collect(Collectors.joining())
                .unwrapOrElse(JavaString.Empty)
                .prepend("<>")
                .append("</>");
    }
}
