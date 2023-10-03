package com.meti.compile.attribute;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.node.Node;

public class NodeAttribute implements Attribute {
    private final Node value;

    public NodeAttribute(Node value) {
        this.value = value;
    }

    @Override
    public Option<Node> asNode() {
        return Some.apply(value);
    }

    @Override
    public JavaString toXML() {
        return value.toXML();
    }
}
