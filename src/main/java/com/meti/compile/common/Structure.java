package com.meti.compile.common;

import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Structure(Text name, List<Node> fields) implements Node {
    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException, StreamException {
        return new Structure(name, attribute.asStreamOfNodes()
                .foldRight(Stream.<Node>builder(), Stream.Builder::add)
                .build().collect(Collectors.toList()));
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
