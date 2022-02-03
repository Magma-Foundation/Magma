package com.meti.app.compile.common.string;

import com.meti.api.collect.java.List;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record String(Input value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new InputAttribute(value);
        throw new AttributeException(type);
    }

    @Deprecated
    private Stream<Attribute.Type> apply2(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Category category) {
        return category == Category.String;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }
}
