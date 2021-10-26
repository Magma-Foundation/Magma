package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeListAttribute;

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
