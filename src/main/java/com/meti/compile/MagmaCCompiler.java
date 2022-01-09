package com.meti.compile;

import com.meti.compile.node.Input;

public record MagmaCCompiler(String input) {
    public String compile() throws CompileException {
        if (input.isBlank()) return input;
        var root = new Input(this.input);
        var node = new MagmaLexer(root).lex();
        return new CRenderer(node)
                .render()
                .getInput();
    }
}
