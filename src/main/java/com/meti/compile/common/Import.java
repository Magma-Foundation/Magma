package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.PackageAttribute;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;

public class Import implements Node {
    private final Packaging packaging_;

    public Import(Packaging packaging_) {
        this.packaging_ = packaging_;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new PackageAttribute(packaging_);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Import;
    }
}
