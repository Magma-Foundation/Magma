package com.meti.magma;

import com.meti.Input;
import com.meti.feature.Import;
import com.meti.feature.IntegerNode;
import com.meti.feature.Node;
import com.meti.feature.Return;

public record MagmaLexer(Input root) {
    public Node lex() {
        if (root.startsWithString("import native ")) {
            var value = root.slice("import native ", root.length());
            return new Import(value);
        } else if (root.startsWithString("return ")) {
            var value = root.slice("return ", root.length());
            return new Return(new IntegerNode(Integer.parseInt(value)));
        } else {
            return new IntegerNode(Integer.parseInt(root.compute()));
        }
    }
}
