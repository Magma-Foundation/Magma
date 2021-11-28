package com.meti.app.process.clang;

import com.meti.app.CompileException;
import com.meti.app.attribute.Attribute;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredRenderer;

public class CImportRenderer extends FilteredRenderer {
    public CImportRenderer(Node value) {
        super(value, Node.Type.Import);
    }

    @Override
    protected String processValid() throws CompileException {
        return "#include <" + value.apply(Attribute.Type.Name).asString() + ".h>\n";
    }
}
