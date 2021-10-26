package com.meti;

import com.meti.feature.Import;
import com.meti.feature.IntegerNode;
import com.meti.feature.Node;

public record MagmaLexer(Input root) {
    Node lexLine() {
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
