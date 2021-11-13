package com.meti;

public record Compiler(String input) {
    private static final int Offset = "def ".length();

    String compile() throws CompileException {
        if (input.isBlank()) return "";
        var paramStart = input.indexOf('(');
        var name = input.substring(Offset, paramStart).trim();
        var typeSeparator = input.indexOf(':');
        var returnSeparator = input.indexOf("=>");
        var returnType = input.substring(typeSeparator + 1, returnSeparator).trim();
        return new CRenderingStage(name, returnType, 0).render();
    }
}
