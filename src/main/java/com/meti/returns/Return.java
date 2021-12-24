package com.meti.returns;

import com.meti.Input;
import com.meti.Node;
import com.meti.attribute.Attribute;
import com.meti.attribute.NodeAttribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Return(Node node) implements Node {
    @Override
    public Option<Attribute> getValue() {
        return new Some<>(new NodeAttribute(node));
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Node with(Node child) {
        return new Return(child);
    }

    public Option<Input> getValueAsInput() {
        return new None<>();
    }

    public Option<Integer> getValueAsInteger() {
        return new None<>();
    }

    public Option<Node> getValueAsNode() {
        return new Some<>(node);
    }
}
