package com.meti.app.clang;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.feature.Import;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.output.Output;
import com.meti.app.compile.node.output.StringOutput;

public final class CImportRenderer extends AbstractRenderer {
    public CImportRenderer(Node node) {
        super(node, Import.Type.Import);
    }

    @Override
    protected Output processDefined() throws CompileException {
        var format = "#include <%s.h>\n";
        var value = node.apply(Attribute.Type.Value);
        var formatted = format.formatted(value);
        return new StringOutput(formatted);
    }
}
