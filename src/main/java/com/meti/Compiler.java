package com.meti;

public record Compiler(ClassPath classPath, String input) {
    String compile() throws CompileException {
        if (input.isBlank()) {
            return "";
        } else if (input.startsWith("import ")) {
            var name = input.substring("import ".length()).trim();
            if (classPath.isDefined(name)) {
                return "#include \"" + name + ".h\"\n";
            } else {
                throw new CompileException("'" + name + "' is not a valid import.");
            }
        } else {
            var start = input.indexOf('{');
            var structureName = input.substring("struct ".length(), start).trim();
            return "struct " + structureName + " {};";
        }
    }
}
