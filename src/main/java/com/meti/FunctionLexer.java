package com.meti;

import java.util.Collections;
import java.util.List;

class FunctionLexer extends AbstractProcessor<Node> {
    private static final int Offset = "def ".length();
    private final String value;

    public FunctionLexer(String value) {
        this.value = value;
    }

    @Override
    protected boolean isValid() {
        return value.startsWith("def ");
    }

    @Override
    protected Node processValid() {
        var paramStart = value.indexOf('(');
        var name = value.substring(Offset, paramStart).trim();
        var typeSeparator = value.indexOf(':');
        var returnSeparator = value.indexOf("=>");
        var returnType = value.substring(typeSeparator + 1, returnSeparator).trim();
        var parameters = new Sequence(Collections.emptyList());
        var identity = returnType.equals("I16")
                ? Primitive.I16.asField(name, parameters)
                : Primitive.U16.asField(name, parameters);
        return new Function(identity, new Block(List.of(new Return(new IntegerNode(0)))));
    }
}
