package com.meti.compile.feature;

import com.meti.compile.node.Node;
import com.meti.compile.node.attribute.Attribute;
import com.meti.compile.node.attribute.NodeListAttribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.List;

public record Block(List<? extends Node> children) implements Node {
    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Children
                ? new Some<>(new NodeListAttribute(children))
                : new None<>();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }
}
