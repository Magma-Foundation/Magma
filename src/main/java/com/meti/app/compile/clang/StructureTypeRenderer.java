package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

class StructureTypeRenderer extends AbstractTypeRenderer {
    StructureTypeRenderer(Input name, Node type) {
        super(name, type);
    }

    @Override
    protected Output processValid() throws AttributeException {
        return type.apply(Attribute.Type.Name)
                .asInput()
                .toOutput()
                .prepend("struct ")
                .appendSlice(" ")
                .appendSlice(name.toOutput().computeRaw());
    }
}
