package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public class Initialization extends Definition {
    private final Node value;

    public Initialization(String name, Node type, Node value, Flag... flags) {
        this(new RootText(name), type, value, flags);
    }

    public Initialization(Input name, Node type, Node value, Flag... flags) {
        this(name, type, value, List.apply(flags));
    }

    public Initialization(Input name, Node type, Node value, List<Flag> flags) {
        super(flags, name, type);
        this.value = value;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Value ? new NodeAttribute(value) : super.apply(type);
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node ? Streams.apply(Attribute.Type.Value) : Streams.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value
                ? new Initialization(name, this.type, attribute.asNode(), flags)
                : super.with(type, attribute);
    }

    @Override
    protected Definition complete(Input name, Node type) {
        return new Initialization(this.name, type, value, flags);
    }

    @Override
    public boolean is(Role role) {
        return role == Role.Initialization;
    }
}
