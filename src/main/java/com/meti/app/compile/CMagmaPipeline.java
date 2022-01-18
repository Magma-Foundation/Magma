package com.meti.app.compile;

import com.meti.app.compile.node.Node;
import com.meti.app.source.Packaging;

public class CMagmaPipeline {
    private final CMagmaNodeResolver resolver = new CMagmaNodeResolver();
    private final CMagmaModifier modifier = new CMagmaModifier();
    private final CFormatter formatter;
    private final CFlattener flattener = new CFlattener();

    public CMagmaPipeline(Packaging thisPackage) {
        this.formatter = new CFormatter(thisPackage);
    }

    Node perform(Node value) throws CompileException {
        var resolved = resolver.transformNodeAST(value);
        var formatted = formatter.transformNodeAST(resolved);
        var modified = modifier.transformNodeAST(formatted);
        return flattener.transformNodeAST(modified);
    }
}
