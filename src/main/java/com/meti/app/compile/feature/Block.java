package com.meti.app.compile.feature;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeListAttribute;

import java.util.List;

public record Block(List<? extends Node> children) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodeListAttribute(children);
        throw new AttributeException("Unknown type: " + type);
    }
}
