package com.meti.compile;

import com.meti.collect.option.Option;
import com.meti.collect.result.ExceptionalStream;
import com.meti.collect.result.Result;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
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
import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public record Application(Path source) {
    private static Result<Node, CompileException> lexExpression(String line, int indent) {
        return Streams.<Function<String, Option<Node>>>from(
                        exp -> new StringLexer(exp).lex(),
                        exp -> new FieldLexer(exp, indent).lex(),
                        exp -> new BlockLexer(exp, indent).lex(),
                        stripped -> new ObjectLexer(stripped).lex(),
                        stripped2 -> new ImportLexer(stripped2).lex(),
                        stripped1 -> new MethodLexer(stripped1, indent).lex(),
                        stripped1 -> new InvocationLexer(new JavaString(stripped1)).lex())
                .map(procedure -> procedure.apply(line.strip()))
                .flatMap(Streams::fromOption)
                .next()
                .into(ThrowableOption::new)
                .orElseThrow(() -> new CompileException("Failed to compile: '%s'.".formatted(line)))
                .flatMapValue(Application::lexTree);
    }

    private static Result<Node, CompileException> lexTree(Node node) {
        return $Result(() -> {
            var withValue = node.findValueAsNode()
                    .map(value -> lexContent(value).mapValue(content -> node.withValue(content).orElse(node)))
                    .orElse(Ok(node)).$();

            return withValue.findChildren().map(oldChildren -> $Result(() -> {
                var newChildren = Streams.fromList(oldChildren)
                        .map(Application::lexContent)
                        .collect(Collectors.exceptionally(Collectors.toList()))
                        .$();

                return withValue.withChildren(newChildren).orElse(withValue);
            })).orElse(Ok(withValue)).$();
        });
    }

    private static Result<Node, CompileException> lexContent(Node content) {
        return content.findValueAsString()
                .and(content.findIndent())
                .map(tuple -> lexExpression(tuple.a(), tuple.b()))
                .into(ThrowableOption::new)
                .orElseThrow(() -> new CompileException("Both the value and the indent must be present. This is not Content."))
                .flatMapValue(Function.identity());
    }

    public Option<Path> run() throws IOException, CompileException {
        if (!Files.exists(source)) return None();

        var input = Files.readString(source);
        var root = new Splitter(input).split()
                .map(line -> lexExpression(line, 0))
                .into(ExceptionalStream::new)
                .mapValues(node -> node.render().orElse(""))
                .collectExceptionally(Collectors.joining())
                .$()
                .orElse("");

        var fileName = source().getFileName().toString();
        var index = fileName.indexOf(".");
        var name = fileName.substring(0, index);
        var target = source().resolveSibling(name + ".mgs");
        Files.writeString(target, root);
        return Some(target);
    }
}