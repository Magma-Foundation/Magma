package com.meti;

public class Compiler {
    private final String input;

    public Compiler(String input) {
        this.input = input;
    }

    static String renderDeclaration(String name, PrimitiveType type) {
        return "\t" + type.renderNative() + " " + name + "=420;\n";
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

    private PrimitiveType resolveTypeName(String typeString) throws ApplicationException {
        return switch (typeString) {
            case "I16" -> PrimitiveType.I16;
            case "U16" -> PrimitiveType.U16;
            default -> throw new ApplicationException("Invalid type: " + typeString);
        };
    }
}
