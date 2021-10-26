package com.meti.clang;

import com.meti.Attribute;
import com.meti.Output;
import com.meti.StringOutput;
import com.meti.feature.Import;
import com.meti.feature.Node;

public final class CImportRenderer extends AbstractRenderer {
    public CImportRenderer(Node node) {
        super(node, Import.Type.Import);
    }

    @Override
    protected Output renderDefined() {
        var format = "#include <%s.h>\n";
        var value = node.apply(Attribute.Type.Value)
                .map(Attribute::asString)
                .orElse("");

        var formatted = format.formatted(value);
        return new StringOutput(formatted);
    }
}
