package com.meti.app.process.clang;

import com.meti.app.CompileException;
import com.meti.app.attribute.Attribute;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredRenderer;

import java.util.Map;

class IntegerTypeRenderer extends FilteredRenderer {
    private final Map<Integer, String> bitsToName = Map.of(
            8, "byte",
            16, "int",
            32, "long",
            64, "long long"
    );

    public IntegerTypeRenderer(Node field) {
        super(field, Node.Type.Primitive);
    }

    @Override
    protected String processValid() throws CompileException {
        var isSigned = value.apply(Attribute.Type.Signed).asBoolean();
        var bits = value.apply(Attribute.Type.Bits).asInt();
        var name = value.apply(Attribute.Type.Name).asString();
        var onset = value.applyOptionally(Attribute.Type.Onset)
                .map(Attribute::asNode)
                .map(value -> value.apply(Attribute.Type.Value))
                .map(Attribute::asString)
                .orElse("");

        var coda = value.applyOptionally(Attribute.Type.Coda)
                .map(Attribute::asNode)
                .map(value -> value.apply(Attribute.Type.Value))
                .map(Attribute::asString)
                .map(value -> "=" + value)
                .orElse("");

        var prefix = isSigned ? "" : "unsigned ";
        var bitString = lookupBits(bits);

        return prefix + bitString + " " + name + onset + coda;
    }

    private String lookupBits(int bits) throws CompileException {
        if (bitsToName.containsKey(bits)) {
            return bitsToName.get(bits);
        } else {
            throw new CompileException("Unknown amount of bits: " + bits);
        }
    }
}
