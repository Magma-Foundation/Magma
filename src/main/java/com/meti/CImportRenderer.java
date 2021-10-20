package com.meti;

public final class CImportRenderer {
    Output render(Node anImport) {
        return new StringOutput("#include <" + anImport.value() + ".h>\n");
    }
}