package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.Output;

class StructureTypeRenderer extends OutputRenderer {
    StructureTypeRenderer(Node identity) {
        super(identity, Node.Type.Structure);
    }

    @Override
    protected Output processValid() throws AttributeException {
        var name = identity.apply(Attribute.Type.Name).asInput();
        return name.toOutput()
                .prepend("struct ")
                .appendSlice(" ")
                .appendSlice(name.toOutput().computeRaw());
    }
}
