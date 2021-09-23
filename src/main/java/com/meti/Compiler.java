package com.meti;

public class Compiler {
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    String compile() throws ApplicationException {
        if (input.isBlank()) {
            return "";
        } else if (input.startsWith("const ")) {
            var typeSeparator = input.indexOf(':');
            var name = input.substring("const ".length(), typeSeparator).trim();
            var typeString = input.substring(typeSeparator + 1, input.indexOf("=")).trim();
            var type = resolveTypeName(typeString);
            return renderDeclaration(name, type);
        } else {
            throw new ApplicationException("Invalid input:" + input);
        }
    }

    static String renderDeclaration(String name, String type) {
        return "\t" + type + " " + name + "=420;\n";
    }

    private String resolveTypeName(String typeString) throws ApplicationException {
        if (typeString.equals("I16")) {
            return "int";
        } else if (typeString.equals("U16")) {
            return "unsigned int";
        } else {
            throw new ApplicationException("Invalid type: " + typeString);
        }
    }
}
