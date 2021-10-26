package com.meti.app.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.feature.Import;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

public final class CImportRenderer extends AbstractRenderer {
    public CImportRenderer(Node node) {
        super(node, Import.Type.Import);
    }

    @Override
    protected Output processDefined() {
        var format = "#include <%s.h>\n";
        Option<Attribute> result;
        try {
            result = new Some<>(node.apply(Attribute.Type.Value));
        } catch (AttributeException e) {
            result = new None<>();
        }
        var value = result
                .map(Attribute::asString)
                .orElse("");

        var formatted = format.formatted(value);
        return new StringOutput(formatted);
    }
}
