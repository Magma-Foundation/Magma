package com.meti.compile;

import com.meti.collect.stream.Collectors;
import com.meti.collect.option.None;
import com.meti.collect.stream.Streams;
import com.meti.collect.option.Option;
import com.meti.compile.external.ImportLexer;
import com.meti.compile.node.Node;
import com.meti.compile.procedure.InvocationLexer;
import com.meti.compile.procedure.MethodLexer;
import com.meti.compile.scope.BlockLexer;
import com.meti.compile.scope.FieldLexer;
import com.meti.compile.scope.ObjectLexer;
import com.meti.compile.string.StringLexer;
import com.meti.java.JavaString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record Application(Path source) {
    private static Node lexExpression(String line, int indent) {
        return Streams.<Function<String, Option<Node>>>from(
                        exp -> new StringLexer(exp).lex(),
                        exp -> new FieldLexer(exp, indent).lex(),
                        exp -> new BlockLexer(exp, indent).lex(),
                        stripped -> new ObjectLexer(stripped).lex(),
                        stripped2 -> new ImportLexer(stripped2).lex(),
                        stripped1 -> new InvocationLexer(new JavaString(stripped1)).lex(),
                        stripped1 -> new MethodLexer(stripped1, indent).lex())
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

    public Option<Path> run() throws IOException {
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