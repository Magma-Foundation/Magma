package com.meti.compile;

import com.meti.compile.clang.CRenderer;
import com.meti.compile.magma.MagmaLexer;
import com.meti.compile.node.Text;

public record MagmaCCompiler(String input) {
    public String compile() throws CompileException {
        if (input.isBlank()) return input;
        var root = new Text(this.input);
        var node = new MagmaLexer(root).lex();
        return new CRenderer(node).render().compute();
    }
}
