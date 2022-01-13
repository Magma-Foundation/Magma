package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.PackageAttribute;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;

public record CFormatter(Packaging thisPackage) implements Formatter {
    @Override
    public Node apply(Node node) throws AttributeException {
        if (node.is(Node.Type.Import)) {
            var thatPackage = node.apply(Attribute.Type.Value).asPackaging();
            var newPackage = thisPackage.relativize(thatPackage);
            return node.with(Attribute.Type.Value, new PackageAttribute(newPackage));
        } else {
            return node;
        }
    }
}
