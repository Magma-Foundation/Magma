package com.meti.app.compile.clang.feature.struct;

import com.meti.app.compile.clang.stage.OutputRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.Output;

public class StructureTypeRenderer extends OutputRenderer {
    public StructureTypeRenderer(Node identity) {
        super(identity, Node.Category.Structure);
    }

    @Override
    protected Output processImpl() throws AttributeException {
        var name = identity.apply(Attribute.Category.Name).asInput();
        return name.toOutput()
                .prepend("struct ")
                .appendSlice(" ")
                .appendSlice(name.toOutput().computeRaw());
    }
}
