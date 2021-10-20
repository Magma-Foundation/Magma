package com.meti;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record Compiler(String input) {
    private static String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }

    String compile() throws CompileException {
        var builder = new StringBuilder();
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        lines.removeIf(String::isBlank);

        for (var line : lines) {
            if (!line.isBlank()) {
                var root = compileLine(line);
                var output = render(root);
                var outputAsString = output.compute();
                builder.append(outputAsString);
            }
        }
        return builder.toString();
    }

    private Output render(Node node) throws CompileException {
        if (node.is(Node.Type.Structure)) {
            return new StructureRenderer(";").render(node);
        } else if (node.is(Node.Type.Import)) {
            return new CImportRenderer().render(node);
        } else if (node.is(Node.Type.Function)) {
            return new CFunctionRenderer().render(node);
        } else {
            throw new CompileException("Cannot render node of class: " + node.getClass());
        }
    }

    private Node compileLine(String line) throws CompileException {
        if (line.startsWith("struct ")) {
            return lexStructure();
        } else if (line.startsWith("import native ")) {
            return lexNativeImport(line);
        } else if (line.startsWith("def ")) {
            return lexFunction();
        } else {
            throw new CompileException("Invalid input: " + line);
        }
    }

    private Node lexNativeImport(String line) {
        var start = "import native ".length();
        var name = line.substring(start);
        return new Import(name);
    }

    private Node lexFunction() {
        var nameStart = "def ".length();
        var nameEnd = input.indexOf('(');
        var name = slice(input, nameStart, nameEnd);

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.indexOf('}') + 1;
        var bodySlice = slice(input, bodyStart, bodyEnd);
        var body = new Content(bodySlice);

        var typeStart = input.indexOf(':') + 1;
        var typeEnd = input.indexOf("=>");
        var typeSlice = slice(input, typeStart + 1, typeEnd);
        var type = resolveTypeName(typeSlice);
        return new Function(new Field(name, type), body);
    }

    private Node lexStructure() {
        var membersStart = input.indexOf('{');
        var membersEnd = input.indexOf('}');
        var name = slice(input, "struct ".length(), membersStart);
        var membersString = slice(input, membersStart + 1, membersEnd);
        var members = lexMembers(membersString);
        return new Structure(name, members);
    }

    private List<Content> lexMembers(String membersString) {
        return Arrays.stream(membersString.split(","))
                .filter(value -> !value.isBlank())
                .map(Content::new)
                .collect(Collectors.toList());
    }

    private Field lexDeclaration(String value) {
        var separator = value.indexOf(':');
        var name = slice(value, 0, separator);
        var typeString = slice(value, separator + 1, value.length());
        var type = this.resolveTypeName(typeString);
        return new Field(name, type);
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