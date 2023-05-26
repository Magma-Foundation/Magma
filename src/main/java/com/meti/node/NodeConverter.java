package com.meti.node;

import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.node.NodeAttribute;

import java.util.Optional;

public abstract class NodeConverter implements Attribute.Converter<Node> {
    @Override
    public Attribute fromValue(Node value) {
        return new NodeAttribute(value);
    }

    @Override
    public Optional<Node> fromAttribute(Attribute value) {
        return value.asNode();
    }
}
