package com.meti.app.compile.common.variable;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.render.AbstractRenderer;
import com.meti.app.compile.text.Output;

public final class VariableRenderer extends AbstractRenderer {
    public VariableRenderer(Node node) {
        super(node);
    }

    @Override
    protected boolean validate() {
        return node.is(Node.Type.Variable);
    }

    @Override
    protected Output createNode() throws AttributeException {
        return node.apply(Attribute.Type.Value)
                .asInput()
                .toOutput();
    }
}
