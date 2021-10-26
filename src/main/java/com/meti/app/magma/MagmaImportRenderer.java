package com.meti.app.magma;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.clang.AbstractRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

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
        Option<Attribute> result;
        try {
            result = new Some<>(node.apply(Attribute.Type.Value));
        } catch (AttributeException e) {
            result = new None<>();
        }
        return result
                .map(Attribute::asString)
                .orElse("");
    }
}
