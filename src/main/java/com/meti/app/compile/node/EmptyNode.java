package com.meti.app.compile.node;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmptyNode implements Node {
    public static final EmptyNode EmptyNode_ = new EmptyNode();

    private EmptyNode() {
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().add("empty", true);
    }

    @Override
    @Deprecated
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return List.createList(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Empty;
    }
}
