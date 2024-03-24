package com.meti;

import java.util.ArrayList;
import java.util.Optional;

public class Compiler {
    static String compile(String input) throws CompileException {
        var output = new ArrayList<String>();
        for (var line : split(input)) {
            output.add(compileLine(line.strip()));
        }

        return String.join("\n", output);
    }

    public static String[] split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        lines.removeIf(String::isBlank);
        return lines.toArray(String[]::new);
    }

    private static String compileLine(String input) throws CompileException {
        var classResult = compileClass(input, 0);
        if (classResult.isPresent()) return classResult.get();

        var importResult = new ImportCompiler(input).compileImport();
        if (importResult.isPresent()) return importResult.get();

        if (input.isEmpty()) {
            return "";
        }

        if (input.startsWith("package ")) {
            return "";
        }

        throw new CompileException("Invalid input: " + input);
    }

    private static Optional<String> compileClass(String input, int indent) {
        return new ClassLexer(input, indent)
                .lex()
                .map(Compiler::lexClassAST)
                .flatMap(ClassNode::render);
    }

    private static ClassNode lexClassAST(ClassNode node) {
        var body2 = node.findBody();
        var value = body2.findValue();
        var indent1 = body2.findIndent();

        var compiledBody = compileStatements(indent1, value);
        return node.withBody(new Content(indent1, compiledBody));
    }

    private static String compileStatements(int indent, String statementsString) {
        var builder = new StringBuilder();
        for (var statement : split(statementsString)) {
            var statementOptional = compileStatement(indent, statement);
            builder.append("\n")
                    .append("\t".repeat(indent + 1))
                    .append(statementOptional.orElse(""));
        }

        return builder.toString();
    }

    private static Optional<String> compileStatement(int indent, String body) {
        if (body.isEmpty()) return Optional.of("");

        var methodString = compileMethod(indent, body);
        if (methodString.isPresent()) return methodString;

        var definitionString = compileDefinition(body);
        if (definitionString.isPresent()) return definitionString;

        return compileClass(body, indent + 1);
    }

    private static Optional<String> compileMethod(int indent, String input) {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var keys = input.substring(0, paramStart).strip();
        var space = keys.lastIndexOf(' ');
        if (space == -1) return Optional.empty();

        var name = keys.substring(space + 1).strip();
        var typeString = keys.substring(0, space).strip();

        var type = new TypeCompiler(typeString).compile();

        var bodyStart = input.indexOf('{');
        if (bodyStart == -1) return Optional.empty();

        var bodyEnd = input.lastIndexOf('}');
        if (bodyEnd == -1) return Optional.empty();

        var body = input.substring(bodyStart + 1, bodyEnd).strip();
        String actualBody;
        if (body.isEmpty()) {
            actualBody = "";
        } else {
            var substring = body.substring(0, body.length() - 1);
            var s = compileDefinition(substring);
            if (s.isEmpty()) return Optional.empty();
            actualBody = "\n" + "\t".repeat(indent + 2) + s.get();
        }

        var wrappedBody = actualBody.isEmpty() ? "{}" : "{" + actualBody + "\n" + "\t".repeat(indent + 1) + "}";
        return Optional.of("def " + name + "() : " + type + " => " + wrappedBody);
    }

    private static Optional<String> compileDefinition(String substring) {
        var compile = new DefinitionLexer(substring).lex();
        if (compile.isEmpty()) return Optional.empty();

        var node = compile.get();
        var value = node.findValue();
        var outputValue = compileValue(value);
        if (outputValue.isEmpty()) return Optional.empty();

        var withValue = node.withValue(new Content(value.findIndent(), outputValue.get()));
        return withValue.render();
    }

    private static Optional<String> compileValue(Node value) {
        return compileInvocation(value.findValue())
                .or(() -> compileInteger(value.findValue()))
                .or(() -> compileString(value.findValue()));
    }

    private static Optional<String> compileInvocation(String value) {
        if(value.endsWith("()")) {
            var name = value.substring(0, value.indexOf('(')).strip();
            return Optional.of(name + "()");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileString(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return Optional.of("\"" + value.substring(1, value.length() - 1) + "\"");
        } else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileInteger(String value) {
        try {
            return Optional.of(String.valueOf(Integer.parseInt(value)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
