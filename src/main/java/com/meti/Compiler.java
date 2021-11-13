package com.meti;

import java.util.Collections;
import java.util.List;

public record Compiler(String input) {
    private static final int Offset = "def ".length();

    String compile() throws CompileException {
        if (input.isBlank()) return "";
        var paramStart = input.indexOf('(');
        var name = input.substring(Offset, paramStart).trim();
        var typeSeparator = input.indexOf(':');
        var returnSeparator = input.indexOf("=>");
        var returnType = input.substring(typeSeparator + 1, returnSeparator).trim();
        var parameters = new Sequence(Collections.emptyList());
        var identity = returnType.equals("I16")
                ? Primitive.I16.asField(name, parameters)
                : Primitive.U16.asField(name, parameters);
        var root = new Function(identity, new Block(List.of(new Return(new IntegerNode(0)))));
        return new CRenderingStage(root).render();
    }
}
