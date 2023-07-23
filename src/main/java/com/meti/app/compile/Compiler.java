package com.meti.app.compile;

import com.meti.app.compile.block.BlockLexer;
import com.meti.app.compile.block.BlockRenderer;
import com.meti.app.compile.clazz.ClassLexer;
import com.meti.app.compile.clazz.ClassTransformer;
import com.meti.app.compile.declare.DeclarationLexer;
import com.meti.app.compile.declare.DeclarationRenderer;
import com.meti.app.compile.function.FunctionLexer;
import com.meti.app.compile.function.FunctionRenderer;
import com.meti.app.compile.imports.ImportLexer;
import com.meti.app.compile.imports.ImportRenderer;
import com.meti.core.*;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.*;

import static com.meti.core.Results.$Result;
import static com.meti.iterate.Collectors.exceptionally;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.joining;

public record Compiler(String_ input) {

    public static Result<String_, CompileException> resolveType(String_ type) {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .applyOptionally(type.unwrap())
                .map(JavaString::fromSlice)
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElse(Err.apply(new CompileException("Unknown type: " + type.unwrap())));
    }

    private static Node lexNode(String_ line) {
        Lexer lexer = new DeclarationLexer(line);
        Lexer lexer1 = new FunctionLexer(line);
        Lexer lexer2 = new BlockLexer(line);
        Lexer lexer3 = new ClassLexer(line);
        Lexer lexer4 = new ImportLexer(line);
        return lexer4.lex().flatMap(result4 -> result4.value())
                .or(lexer3.lex().flatMap(result3 -> result3.value()))
                .or(lexer2.lex().flatMap(result2 -> result2.value()))
                .or(lexer1.lex().flatMap(result1 -> result1.value()))
                .or(lexer.lex().flatMap(result -> result.value()))
                .unwrapOrElse(Content.ofContent(line));
    }

    private static Result<Node, CompileException> lexTree(String_ line) {
        return Results.$Result(CompileException.class, () -> {
            var node = lexNode(line);

            var withReturns = node.returns().flatMap(Node::value)
                    .map(Compiler::resolveType)
                    .map(value -> value.mapValue(Content::new))
                    .map(value -> value.mapValue(node::withReturns))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(node))
                    .$();

            var withBody = withReturns.body().flatMap(Node::value)
                    .map(Compiler::lexTree)
                    .map(value -> value.mapValue(withReturns::withBody))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withReturns))
                    .$();

            return withBody.lines().map(lines -> lines.iter()
                            .map(node1 -> node1.value()
                                    .map(Compiler::lexTree)
                                    .unwrapOrElse(Err.apply(new CompileException("No value present in list."))))
                            .collect(exceptionally(JavaList.asList())))
                    .map(value -> value.mapValue(withBody::withLines))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();
        });
    }

    private static Result<String_, CompileException> renderTree(Node transformed) {
        return $Result(CompileException.class, () -> {
            var withBody = transformed.body().map(Compiler::renderTree)
                    .map(r -> r.mapValue(body -> transformed.withBody(new Content(body))))
                    .unwrapOrElse(Ok.apply(Some.apply(transformed))).$()
                    .unwrapOrElse(transformed);

            var map = transformed.lines().map(lines -> lines.iter().map(Compiler::renderTree)
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectToResult(JavaList.asList()))
                    .map(value -> Results.invert(value.mapValue(transformed::withLines)))
                    .flatMap(value -> value)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();

            return renderNode(map).$();
        });
    }

    private static Result<String_, CompileException> renderNode(Node transformed) {
        Set<? extends Renderer> renderers = JavaSet.of(new BlockRenderer(transformed),
                new DeclarationRenderer(transformed),
                new FunctionRenderer(transformed),
                new ImportRenderer(transformed),
                new ContentRenderer(transformed));

        return renderers.iter()
                .map(Renderer::render)
                .flatMap(Iterators::fromOption)
                .head()
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElse(Err.apply(new CompileException("Failed to render: " + transformed)));
    }

    public Result<String_, CompileException> compile() {
        return new Splitter(input).split()
                .filter(line -> !line.strip().startsWith("package "))
                .map(this::compileNode)
                .into(ResultIterator::new)
                .collectToResult(joining(Empty))
                .mapValue(inner -> inner.unwrapOrElse(Empty));
    }

    private Result<String_, CompileException> compileNode(String_ line) {
        return $Result(CompileException.class, () -> {
            var withLines = lexTree(line).$();
            var transformed = new ClassTransformer(withLines)
                    .transform()
                    .unwrapOrElse(withLines);

            return renderTree(transformed).$();
        });
    }
}