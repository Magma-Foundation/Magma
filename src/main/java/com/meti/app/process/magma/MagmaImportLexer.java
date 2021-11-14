package com.meti.app.process.magma;

import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredLexer;

public class MagmaImportLexer extends FilteredLexer {
    public MagmaImportLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean isValid() {
        return input.startsWithSlice("import native ");
    }

    @Override
    protected Node processValid() throws CompileException {
        var name = input.slice("import native ".length(), input.length());
/*        String formattedName;
        if (name.startsWith("\"") && name.endsWith("\"")) {
            formattedName = name.substring(1, name.length() - 1);
        } else {
            formattedName = name;
        }*/
        return new Node() {
            @Override
            public boolean is(Type type) {
                return type == Type.Import;
            }
        };
    }
}
