package com.meti;

public final class CImportRenderer implements Renderer {
    @Override
    public Output render(Node anImport) throws CompileException {
        return new StringOutput("#include <" + anImport.apply(Attribute.Group.Value).asString() + ".h>\n");
    }
}