package com.meti.magma;

import com.meti.clang.AbstractRenderer;
import com.meti.compile.node.Node;
import com.meti.compile.node.attribute.Attribute;
import com.meti.compile.node.output.Output;
import com.meti.compile.node.output.StringOutput;

class MagmaImportRenderer extends AbstractRenderer {
    public static final String ImportNativePrefix = "import native ";

    public MagmaImportRenderer(Node node) {
        super(node, Node.Type.Import);
    }

    @Override
    protected Output processDefined() {
        return new StringOutput(ImportNativePrefix + compute());
    }

    private String compute() {
        return node.apply(Attribute.Type.Value)
                .map(Attribute::asString)
                .orElse("");
    }
}
