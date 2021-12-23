package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record MCCompiler(Input input) {
    String compile() throws CompileException {
        return lexInteger()
                .map(this::render)
                .orElseThrow(() -> new CompileException("Invalid input."));
    }

    private Option<IntegerNode> lexInteger() {
        try {
            var value = input.compute();
            Integer.parseInt(value);
            return new Some<>(new IntegerNode(value));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }

    private String render(IntegerNode integerNode) {
        return integerNode.getValue();
    }
}