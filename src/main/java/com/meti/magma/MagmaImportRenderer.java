package com.meti.magma;

import com.meti.Compiler;
import com.meti.attribute.Attribute;
import com.meti.clang.AbstractRenderer;
import com.meti.feature.Node;
import com.meti.output.Output;
import com.meti.output.StringOutput;

class MagmaImportRenderer extends AbstractRenderer {
    public MagmaImportRenderer(Node node) {
        super(node, Node.Type.Import);
    }

    @Override
    protected Output renderDefined() {
        return new StringOutput(Compiler.ImportNativePrefix + node
                .apply(Attribute.Type.Value)
                .map(Attribute::asString)
                .orElse(""));
    }
}
