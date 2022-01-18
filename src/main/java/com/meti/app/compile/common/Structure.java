package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodesAttribute;
import com.meti.app.compile.attribute.TextAttribute;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Structure(Text name, java.util.List fields) implements Node {
    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new List<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Structure(name, attribute.asStreamOfNodes().collect(Collectors.toList()));
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) {
        return group == Attribute.Group.Declarations
                ? Stream.of(Attribute.Type.Fields)
                : Stream.empty();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new TextAttribute(name);
        if (type == Attribute.Type.Fields) return new NodesAttribute(fields);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Structure;
    }
}
