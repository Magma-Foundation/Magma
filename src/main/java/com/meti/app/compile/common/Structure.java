package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;
import com.meti.app.compile.text.Input;

public record Structure(Input name, List<Node> fields) implements Node {
    public Structure(Input name, Node... fields) {
        this(name, List.apply(fields));
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new InputAttribute(name);
        if (type == Attribute.Type.Fields) return new NodesAttribute(fields);
        throw new AttributeException(type);
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply(Attribute.Group group) {
        return group == Attribute.Group.Definitions
                ? Streams.apply(Attribute.Type.Fields)
                : Streams.empty();
    }


    @Override
    public boolean is(Type type) {
        return type == Type.Structure;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return new Structure(name, attribute.asStreamOfNodes()
                    .foldRight(List.createList(), List::add));
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }
}
