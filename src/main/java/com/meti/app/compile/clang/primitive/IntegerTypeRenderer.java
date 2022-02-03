package com.meti.app.compile.clang.primitive;

import com.meti.app.compile.clang.stage.OutputRenderer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.render.RenderException;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Output;

public class IntegerTypeRenderer extends OutputRenderer {
    public IntegerTypeRenderer(Node identity) {
        super(identity, Node.Category.Integer);
    }

    @Override
    protected Output processImpl() throws CompileException {
        var name = identity.apply(Attribute.Category.Name).asInput();
        var type = identity.apply(Attribute.Category.Type).asType();

        var isSigned = type.apply(Attribute.Category.Sign).asBoolean();
        var bits = type.apply(Attribute.Category.Bits).asInteger();
        var prefix = switch (bits) {
            case 8 -> "char";
            case 16 -> "int";
            default -> throw new RenderException("Unknown bit quantity: " + bits);
        };

        var signedFlag = isSigned ? "" : "unsigned ";

        var buffer = name.toTrimmedOutput()
                .prepend(" ")
                .prepend(prefix)
                .prepend(signedFlag);
        if (identity.is(Node.Category.Initialization)) {
            var value = identity.apply(Attribute.Category.Value)
                    .asNode()
                    .apply(Attribute.Category.Value)
                    .asOutput();
            return buffer.appendSlice("=").appendOutput(value);
        } else {
            return buffer;
        }
    }
}
