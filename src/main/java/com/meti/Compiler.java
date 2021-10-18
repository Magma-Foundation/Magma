package com.meti;

public record Compiler(String input) {
    String compile() {
        String output;
        if (input.startsWith("def ")) {
            var nameStart = "def ".length();
            var nameEnd = input.indexOf('(');
            var name = slice(nameStart, nameEnd);

            var bodyStart = input.indexOf('{');
            var bodyEnd = input.indexOf('}') + 1;
            var body = slice(bodyStart, bodyEnd);

            var typeStart = input.indexOf(':') + 1;
            var typeEnd = input.indexOf("=>");
            var typeSlice = slice(typeStart + 1, typeEnd);
            var type = switch (typeSlice) {
                case "I16" -> "int";
                case "U16" -> "unsigned int";
                case "Void" -> "void";
                default -> "";
            };
            output = new CFunctionRenderer().render(name, type, body);
        } else {
            output = "";
        }
        return output;
    }

    private String slice(int start, int end) {
        return input.substring(start, end).trim();
    }
}