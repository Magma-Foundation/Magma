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

        var definitionString = new DefinitionCompiler(body).compileDefinition();
        if (definitionString.isPresent()) return definitionString;

        return compileClass(body, indent + 1);
    }
}
