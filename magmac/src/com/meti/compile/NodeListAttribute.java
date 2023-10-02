package com.meti.compile;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;

public record NodeListAttribute(List<? extends Node> values) implements Attribute {
    public static Attribute from(List<? extends Node> values) {
        return new NodeListAttribute(values);
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
