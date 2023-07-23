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

    private static Result<Node, CompileException> lexNode(String_ line) {
        return JavaList.<Lexer>from(new DeclarationLexer(line),
                        new FunctionLexer(line),
                        new BlockLexer(line),
                        new ClassLexer(line),
                        new ImportLexer(line))
                .iter()
                .map(Lexer::lex)
                .flatMap(Iterators::fromOption)
                .into(ResultIterator::new)
                .head()
                .unwrapOrElse(Ok.apply(new Content(line)));
    }

    private static Result<Node, CompileException> lexTree(String_ line) {
        return Results.$Result(CompileException.class, () -> {
            var node = lexNode(line).$();

            var withParameters = node.parameters().map(lines -> lines.iter()
                            .map(Compiler::unwrapValue)
                            .collect(exceptionally(JavaSet.asSet())))
                    .map(value -> value.mapValue(node::withParameters))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(node))
                    .$();

            var withReturns = withParameters.returns().flatMap(Node::value)
                    .map(Compiler::resolveType)
                    .map(value -> value.mapValue(Content::new))
                    .map(value -> value.mapValue(withParameters::withReturns))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withParameters))
                    .$();

            var withBody = withReturns.body().flatMap(Node::value)
                    .map(Compiler::lexTree)
                    .map(value -> value.mapValue(withReturns::withBody))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withReturns))
                    .$();

            return withBody.lines().map(lines -> lines.iter()
                            .map(Compiler::unwrapValue)
                            .collect(exceptionally(JavaList.asList())))
                    .map(value -> value.mapValue(withBody::withLines))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();
        });
    }

    private static Result<Node, CompileException> unwrapValue(Node node1) {
        return node1.value()
                .map(Compiler::lexTree)
                .unwrapOrElse(Err.apply(new CompileException("No value present in list.")));
    }

    private static Result<String_, CompileException> renderTree(Node transformed) {
        return $Result(CompileException.class, () -> {
            var withParameters = transformed.parameters().map(lines -> lines.iter().map(Compiler::renderTree)
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectToResult(JavaSet.asSet()))
                    .map(value -> Results.invert(value.mapValue(transformed::withParameters)))
                    .flatMap(value -> value)
                    .unwrapOrElse(Ok.apply(transformed))
                    .$();

            var withBody = withParameters.body().map(Compiler::renderTree)
                    .map(r -> r.mapValue(body -> withParameters.withBody(new Content(body))))
                    .unwrapOrElse(Ok.apply(Some.apply(withParameters))).$()
                    .unwrapOrElse(withParameters);

            var withLines = withBody.lines().map(lines -> lines.iter().map(Compiler::renderTree)
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectToResult(JavaList.asList()))
                    .map(value -> Results.invert(value.mapValue(withBody::withLines)))
                    .flatMap(value -> value)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();

            return renderNode(withLines).$();
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