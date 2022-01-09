package com.meti.compile;

public record MagmaCCompiler(String input) {
    public String compile() throws CompileException {
        if (input.isBlank()) return input;
        var root = new Input(this.input);
        var node = new MagmaLexer(root).lexNodeTree();
        return new CRenderer(node)
                .render()
                .getInput();
    }
}
