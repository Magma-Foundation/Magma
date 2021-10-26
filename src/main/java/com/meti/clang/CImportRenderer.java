package com.meti.clang;

import com.meti.compile.feature.Import;
import com.meti.compile.node.Node;
import com.meti.compile.node.attribute.Attribute;
import com.meti.compile.node.output.Output;
import com.meti.compile.node.output.StringOutput;

public final class CImportRenderer extends AbstractRenderer {
    public CImportRenderer(Node node) {
        super(node, Import.Type.Import);
    }

    @Override
    protected Output processDefined() {
        var format = "#include <%s.h>\n";
        var value = node.apply(Attribute.Type.Value)
                .map(Attribute::asString)
                .orElse("");

        var formatted = format.formatted(value);
        return new StringOutput(formatted);
    }
}
