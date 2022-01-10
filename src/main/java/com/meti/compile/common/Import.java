package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.PackageAttribute;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;

public record Import(Packaging packaging_) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new PackageAttribute(packaging_);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Import;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Import(attribute.asPackaging());
    }
}
