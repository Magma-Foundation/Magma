package com.meti.clang;

import com.meti.attribute.Attribute;
import com.meti.feature.Import;
import com.meti.feature.Node;
import com.meti.output.Output;
import com.meti.output.StringOutput;

public final class CImportRenderer extends AbstractRenderer {
    public CImportRenderer(Node node) {
        super(node, Import.Type.Import);
    }

    @Override
    protected Output processValid() {
        var format = "#include <%s.h>\n";
        var value = node.apply(Attribute.Type.Value)
                .map(Attribute::asString)
                .orElse("");

        var formatted = format.formatted(value);
        return new StringOutput(formatted);
    }
}
