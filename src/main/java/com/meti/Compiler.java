package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }

    String compile() {
        String output;
        if (input.startsWith("struct ")) {
            output = lexStructure();
        } else if (input.startsWith("import native ")) {
            output = lexNativeImport();
        } else if (input.startsWith("def ")) {
            output = lexFunction();
        } else {
            output = "";
        }
        return output;
    }

    private String lexNativeImport() {
        var name = input.substring("import native ".length());
        return new IncludeDirectiveRenderer().render(name);
    }

    private String lexFunction() {
        var nameStart = "def ".length();
        var nameEnd = input.indexOf('(');
        var name = slice(input, nameStart, nameEnd);

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.indexOf('}') + 1;
        var body = slice(input, bodyStart, bodyEnd);

        var typeStart = input.indexOf(':') + 1;
        var typeEnd = input.indexOf("=>");
        var typeSlice = slice(input, typeStart + 1, typeEnd);
        var type = resolveTypeName(typeSlice);
        return new CFunctionRenderer().render(name, type, body);
    }

    private String lexStructure() {
        var membersStart = input.indexOf('{');
        var membersEnd = input.indexOf('}');
        var name = slice(input, "struct ".length(), membersStart);
        var membersString = slice(input, membersStart + 1, membersEnd);
        var members = lexMembers(membersString);
        return new StructureRenderer(";").render(name, members) + ";";
    }

    private List<String> lexMembers(String membersString) {
        return Arrays.stream(membersString.split(","))
                .filter(value -> !value.isBlank())
                .map(this::lexMember)
                .collect(Collectors.toList());
    }

    private String lexMember(String value) {
        var separator = value.indexOf(':');
        var name = slice(value, 0, separator);
        var type = slice(value, separator + 1, value.length());
        return resolveTypeName(type) + " " + name + ";";
    }

    private String resolveTypeName(String name) {
        return switch (name) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> "";
        };
    }
}