package com.meti.compile;

import com.meti.compile.attribute.*;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.source.Packaging;

public final class CFormatter implements Formatter {
    private final Packaging thisPackage;
    private int counter = 0;

    public CFormatter(Packaging thisPackage) {
        this.thisPackage = thisPackage;
    }

    @Override
    public Node apply(Node node) throws AttributeException {
        if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
            var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
            var oldName = oldIdentity.apply(Attribute.Type.Name).asText();
            Text newName;
            if (oldName.isEmpty()) {
                newName = new Text("__lambda" + counter++ + "__");
            } else {
                newName = oldName;
            }
            var newIdentity = oldIdentity.with(Attribute.Type.Name, new TextAttribute(newName));
            return node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
        } else if (node.is(Node.Type.Import)) {
            var thatPackage = node.apply(Attribute.Type.Value).asPackaging();
            var newPackage = thisPackage.relativize(thatPackage);
            return node.with(Attribute.Type.Value, new PackageAttribute(newPackage));
        } else {
            return node;
        }
    }
}
