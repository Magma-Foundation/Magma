package com.meti.app.compile.common;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.TypeAttribute;

public class ReferenceType extends AbstractNode implements Type {
    private final Type value;

    public ReferenceType(Type value) {
        this.value = value;
    }

    @Override
    public boolean isAssignableTo(Type other) throws AttributeException {
        if (!other.is(Node.Category.Reference)) return false;
        var inner = other.apply(Attribute.Category.Value).asType();
        return value.isAssignableTo(inner);
    }

    @Override
    public Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Type ? Streams.apply(Attribute.Category.Value) : Streams.empty();
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Reference;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        if (category == Attribute.Category.Value) return new TypeAttribute(value);
        throw new AttributeException(category);
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return category == Attribute.Category.Value
                ? new ReferenceType(attribute.asType())
                : this;
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode().addJSONable("refersTo", value);
    }
}
