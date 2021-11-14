package com.meti.app.process.magma;

import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.StringAttribute;
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
        var name = input.slice("import native ".length(), input.length()).trim();
        String formattedName;
        if (name.startsWith("\"") && name.endsWith("\"")) {
            formattedName = name.substring(1, name.length() - 1);
        } else {
            formattedName = name;
        }
        return new Node() {
            @Override
            public Attribute apply(Attribute.Type type) throws AttributeException {
                if (type == Attribute.Type.Name) return new StringAttribute(formattedName);
                throw new AttributeException(type);
            }

            @Override
            public boolean is(Type type) {
                return type == Type.Import;
            }
        };
    }
}
