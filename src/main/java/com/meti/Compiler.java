package com.meti;

public record Compiler(String input) {
    private static final int Offset = "def ".length();

    String compile() {
        if (input.isBlank()) return "";
        var paramStart = input.indexOf('(');
        var name = input.substring(Offset, paramStart);
        return "int " + name + "(){return 0;}";
    }
}
