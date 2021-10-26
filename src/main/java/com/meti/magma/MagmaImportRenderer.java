package com.meti.magma;

import com.meti.attribute.Attribute;
import com.meti.clang.AbstractRenderer;
import com.meti.feature.Node;
import com.meti.output.Output;
import com.meti.output.StringOutput;

class MagmaImportRenderer extends AbstractRenderer {
    public static final String ImportNativePrefix = "import native ";

    public MagmaImportRenderer(Node node) {
        super(node, Node.Type.Import);
    }

    @Override
    protected Output processValid() {
        return new StringOutput(ImportNativePrefix + compute());
    }

    private String compute() {
        return node.apply(Attribute.Type.Value)
                .map(Attribute::asString)
                .orElse("");
    }
}
