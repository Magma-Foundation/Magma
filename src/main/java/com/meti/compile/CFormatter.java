package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.PackageAttribute;
import com.meti.compile.node.Node;
import com.meti.option.Some;
import com.meti.source.Packaging;

public record CFormatter(Packaging thisPackage) implements Formatter {
    @Override
    public Node apply(Node node) throws FormattingException {
        try {
            return new Some<>(node)
                    .filter(value -> value.is(Node.Type.Import))
                    .map(value -> value.apply(Attribute.Type.Value))
                    .map(Attribute::asPackaging)
                    .map(thisPackage::relativize)
                    .map(PackageAttribute::new)
                    .map(value -> node.with(Attribute.Type.Value, value))
                    .orElse(node);
        } catch (AttributeException e) {
            throw new FormattingException(e);
        }
    }
}
