package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.PackageAttribute;
import com.meti.app.source.Packaging;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Import(Packaging packaging_) implements Node {
    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        if (category == Attribute.Category.Value) return new PackageAttribute(packaging_);
        throw new AttributeException(category);
    }

    @Deprecated
    private Stream<Attribute.Category> apply2(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.api.collect.stream.Stream<Attribute.Category> apply(Attribute.Group group) throws AttributeException {
        return List.createList(apply2(group).collect(Collectors.toList())).stream();
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Import;
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return new Import(attribute.asPackaging());
    }
}
