package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record Application(Path source) {
    private static Node lexExpression(String line, int indent) {
        return Streams.<Function<String, Option<Node>>>from(
                        exp -> new StringLexer(exp).lex(),
                        exp -> new FieldLexer(exp, indent).lex(),
                        exp -> new BlockLexer(exp, indent).lex(),
                        Application::compileObject,
                        Application::compileImport,
                        stripped1 -> new InvocationLexer(stripped1).lex(),
                        stripped1 -> compileMethod(stripped1, indent))
                .map(procedure -> procedure.apply(line.strip()))
                .flatMap(Streams::fromOption)
                .next()
                .map(Application::lexTree)
                .orElse(None::None);
    }

    private static Node lexTree(Node node) {
        var withValue = node.findValueAsNode().flatMap(value -> lexContent(value)
                        .flatMap(node::withValue))
                .orElse(node);

        return withValue.findChildren().flatMap(children -> {
            var newChildren = Streams.fromList(children)
                    .map(Application::lexContent)
                    .flatMap(Streams::fromOption)
                    .collect(Collectors.toList());

            return withValue.withChildren(newChildren);
        }).orElse(withValue);
    }

    private static Option<Node> lexContent(Node content) {
        return content.findValueAsString()
                .and(content.findIndent())
                .map(tuple -> lexExpression(tuple.a(), tuple.b()));
    }

    private static Option<Node> compileMethod(String stripped, int indent) {
        var paramStart = stripped.indexOf('(');
        var paramEnd = stripped.indexOf(')');
        var contentStart = stripped.indexOf('{');

        if (paramStart == -1 || paramEnd == -1 || contentStart == -1) return None();

        var keyString = stripped.substring(0, paramStart);
        var space = keyString.lastIndexOf(' ');
        if (space == -1) return None();

        var name = keyString.substring(space + 1).strip();
        var featuresString = keyString.substring(0, space).strip();

        var typeSeparator = featuresString.lastIndexOf(' ');
        var type = new TypeCompiler(featuresString.substring(typeSeparator + 1).strip()).compile();
        var annotations = Streams.from(featuresString.substring(0, typeSeparator).strip().split(" "))
                .filter(token -> token.startsWith("@"))
                .map(token -> token.substring(1))
                .map(TypeCompiler::new)
                .map(TypeCompiler::compile)
                .collect(Collectors.toList());

        var content = lexExpression(stripped.substring(contentStart).strip(), indent);
        var more = stripped.substring(paramEnd + 1, contentStart).strip();

        var moreOutputValue = more.startsWith("throws ") ? Some(new TypeCompiler(more.substring("throws ".length())).compile()) : None();

        return Some(new MethodNode(indent, moreOutputValue, annotations, name, type, content));
    }

    private static Option<Node> compileObject(String stripped) {
        if (stripped.startsWith("public class ")) {
            var start = stripped.indexOf("{");

            var name = stripped.substring("public class ".length(), start).strip();
            var content = stripped.substring(start);
            var contentOutput = lexExpression(content, 0);

            var flags = List.of("export");
            return Some(new ObjectNode(flags, name, contentOutput));
        }
        return None();
    }

    private static Option<Node> compileImport(String stripped) {
        if (stripped.startsWith("import ")) {
            var isStatic = stripped.startsWith("import static ");
            var content = stripped.substring(isStatic ? "import static ".length() : "import ".length());

            var last = content.lastIndexOf('.');
            var child = content.substring(last + 1).strip();
            var parent = content.substring(0, last).strip();

            return Some(child.equals("*") ? new ImportAllNode(parent) : new ImportChildNode(child, parent));
        }
        return None();
    }

    Option<Path> run() throws IOException {
        if (!Files.exists(source)) return None();

        var input = Files.readString(source);
        var root = new Splitter(input).split()
                .map(line -> lexExpression(line, 0))
                .map(Node::render)
                .flatMap(Streams::fromOption)
                .collect(Collectors.joining())
                .orElse("");

        var fileName = source().getFileName().toString();
        var index = fileName.indexOf(".");
        var name = fileName.substring(0, index);
        var target = source().resolveSibling(name + ".mgs");
        Files.writeString(target, root);
        return Some(target);
    }
}