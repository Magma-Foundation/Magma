package com.meti;

public record Compiler(String input) {
    String compile() throws CompileException {
        if (input.isBlank()) {
            return "";
        } else if (input.startsWith("import ")) {
            throw new CompileException("Unknown import.");
        } else {
            var start = input.indexOf('{');
            var structureName = input.substring("struct ".length(), start).trim();
            return "struct " + structureName + " {};";
        }
    }
}
