package com.meti.process;

import com.meti.CompileException;
import com.meti.attribute.Attribute;
import com.meti.node.Node;

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
        var preEquality = value.apply(Attribute.Type.PreEquality)
                .asNode()
                .apply(Attribute.Type.Value)
                .asString();

        var prefix = isSigned ? "" : "unsigned ";
        var bitString = lookupBits(bits);

        return prefix + bitString + " " + name + preEquality;
    }

    private String lookupBits(int bits) throws CompileException {
        if (bitsToName.containsKey(bits)) {
            return bitsToName.get(bits);
        } else {
            throw new CompileException("Unknown amount of bits: " + bits);
        }
    }
}
