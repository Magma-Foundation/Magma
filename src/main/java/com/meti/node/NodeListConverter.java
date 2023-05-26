package com.meti.node;

import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.node.NodeListAttribute;

import java.util.List;
import java.util.Optional;

public abstract class NodeListConverter implements Attribute.Converter<List<Node>> {
    @Override
    public Attribute fromValue(List<Node> value) {
        return new NodeListAttribute(value);
    }

    @Override
    public Optional<List<Node>> fromAttribute(Attribute value) {
        return value.asNodeList();
    }
}
