package com.meti.app.compile.clang;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.render.RenderException;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

public class IntegerTypeRenderer extends AbstractTypeRenderer {
    public IntegerTypeRenderer(Input name, Node type, Node.Type nodeType) {
        super(name, type, nodeType);
    }

    @Override
    protected Output processValid() throws CompileException {
        var isSigned = type.apply(Attribute.Type.Sign).asBoolean();
        var bits = type.apply(Attribute.Type.Bits).asInteger();
        var suffix = switch (bits) {
            case 8 -> "char";
            case 16 -> "int";
            default -> throw new RenderException("Unknown bit quantity: " + bits);
        };

        var signedFlag = isSigned ? "" : "unsigned ";
        return name.toOutput().prepend(signedFlag).prepend(" ").prepend(suffix).prepend(" ");
    }
}
