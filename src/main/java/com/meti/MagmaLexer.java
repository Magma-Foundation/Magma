package com.meti;

import com.meti.feature.Import;
import com.meti.feature.IntegerNode;
import com.meti.feature.Node;

public record MagmaLexer(String line) {
    Node lexLine() {
        if (line.startsWith("import native ")) {
            var value = slice(line, "import native ", line.length());
            return new Import(value);
        } else if (line.startsWith("return ")) {
            var value = slice(line, "return ", line.length());
            return new Return(new IntegerNode(Integer.parseInt(value)));
        } else {
            return new IntegerNode(Integer.parseInt(line));
        }
    }

    public static String slice(String line, String prefix, int end) {
        var prefixLength = prefix.length();
        var slice = line.substring(prefixLength, end);
        return slice.trim();
    }
}
