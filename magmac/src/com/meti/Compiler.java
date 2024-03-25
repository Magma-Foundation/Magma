package com.meti;

import com.meti.lex.*;
import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        var value = body2.apply("value").flatMap(Attribute::asString).orElseThrow();
        var indent1 = (int) body2.apply("indent").flatMap(Attribute::asInt).orElseThrow();

        var compiledBody = compileStatements(indent1, value);
        return node.withBody(new Content(compiledBody, indent1));
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

        var withValue = node.withValue(new Content(outputValue.get(), value.apply("indent").flatMap(Attribute::asInt).orElseThrow()));
        return withValue.render();
    }

    private static Optional<String> compileValue(Node value) {
        var value1 = value.apply("value").flatMap(Attribute::asString).orElseThrow();
        return compileInvocation(value1)
                .or(() -> new IntegerCompiler(value1).compileInteger())
                .or(() -> new StringCompiler(value1).compileString())
                .or(() -> Optional.of(value1));
    }

    private static Optional<Node> lexImpl(String value) {
        return Stream.<Lexer>of(
                        new FieldLexer(value),
                        new InvocationLexer(value))
                .map(Lexer::lex)
                .flatMap(Optional::stream)
                .findFirst();
    }

    private static Optional<String> compileInvocation(String input) {
        return lexImpl(input)
                .map(node -> node.mapNodes(Compiler::compileContentToNode).orElse(node))
                .map(node -> node.mapNodeLists(Compiler::compileContentListToNodes).orElse(node))
                .flatMap(Node::render);
    }

    private static Optional<List<Node>> compileContentListToNodes(List<Node> arguments) {
        var list = arguments.stream()
                .map(Compiler::compileContentToNode)
                .toList();

        if (list.stream().anyMatch(Optional::isEmpty)) return Optional.empty();

        var flattened = list.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        return Optional.of(flattened);
    }

    private static Optional<Node> compileContentToNode(Node node) {
        return compileValue(node).map(inner -> new Content(inner, 0));
    }
}
