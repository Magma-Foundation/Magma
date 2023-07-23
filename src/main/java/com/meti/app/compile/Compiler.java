package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.app.compile.block.BlockLexer;
import com.meti.app.compile.block.BlockRenderer;
import com.meti.app.compile.clazz.ClassLexer;
import com.meti.app.compile.clazz.ClassTransformer;
import com.meti.app.compile.declare.DeclarationLexer;
import com.meti.app.compile.declare.DeclarationRenderer;
import com.meti.app.compile.function.FunctionRenderer;
import com.meti.app.compile.function.MethodLexer;
import com.meti.app.compile.imports.ImportLexer;
import com.meti.app.compile.imports.ImportRenderer;
import com.meti.core.*;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.*;

import static com.meti.core.Results.$Result;
import static com.meti.core.Results.invert;
import static com.meti.iterate.Collectors.exceptionally;
import static com.meti.java.JavaString.*;

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
        return JavaList.<Lexer>of(
                        new ClassLexer(line),
                        new BlockLexer(line),
                        new MethodLexer(line),
                        new DeclarationLexer(line),
                        new ImportLexer(line))
                .iter()
                .map(Lexer::lex)
                .flatMap(Iterators::fromOption)
                .into(ResultIterator::new)
                .head()
                .unwrapOrElse(Ok.apply(new Content(line)));
    }

    private static Result<Node, CompileException> lexTree(String_ line) {
        if (line.isEmpty()) {
            return Err.apply(new CompileException("Input cannot be empty."));
        }

        return $Result(CompileException.class, () -> {
            var node = lexNode(line).$();

            var withParameters = node.apply(fromSlice("parameters")).flatMap(Attribute::asSetOfNodes).map(lines -> lines.iter()
                            .map(Compiler::unwrapValue)
                            .collect(exceptionally(JavaSet.asSet())))
                    .map(value -> value.mapValue(parameters -> node.with(fromSlice("parameters"), new NodeSetAttribute(parameters))))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(node))
                    .$();

            var withReturns = withParameters.apply(fromSlice("returns")).flatMap(Attribute::asNode).flatMap(node2 -> node2.apply(fromSlice("value")).flatMap(Attribute::asString))
                    .map(Compiler::resolveType)
                    .map(value -> value.mapValue(Content::new))
                    .map(value -> value.mapValue(returns -> withParameters.with(fromSlice("returns"), new NodeAttribute(returns))))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withParameters))
                    .$();

            var withBody = withReturns.apply(fromSlice("body")).flatMap(Attribute::asNode).flatMap(node1 -> node1.apply(fromSlice("value")).flatMap(Attribute::asString))
                    .map(Compiler::lexTree)
                    .map(value -> value.mapValue(body -> withReturns.with(fromSlice("body"), new NodeAttribute(body))))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withReturns))
                    .$();

            return withBody.apply(JavaString.fromSlice("lines")).flatMap(Attribute::asListOfNodes).map(lines -> lines.iter()
                            .map(Compiler::unwrapValue)
                            .collect(exceptionally(JavaList.asList())))
                    .map(value -> value.mapValue(lines1 -> withBody.with(fromSlice("lines"), new NodeListAttribute(lines1))))
                    .flatMap(Results::invert)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();
        }).mapErr(err -> new CompileException("Failed to lex line: " + line.unwrap(), err));
    }

    private static Result<Node, CompileException> unwrapValue(Node node1) {
        return node1.apply(fromSlice("value")).flatMap(Attribute::asString)
                .map(Compiler::lexTree)
                .unwrapOrElse(Err.apply(new CompileException("No value present in list.")));
    }

    private static Result<String_, CompileException> renderTree(Node transformed) {
        return $Result(CompileException.class, () -> {
            var withParameters = transformed.apply(fromSlice("parameters")).flatMap(Attribute::asSetOfNodes).map(lines -> lines.iter().map(Compiler::renderTree)
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectToResult(JavaSet.asSet()))
                    .map(value -> invert(value.mapValue(parameters -> transformed.with(fromSlice("parameters"), new NodeSetAttribute(parameters)))))
                    .flatMap(value -> value)
                    .unwrapOrElse(Ok.apply(transformed))
                    .$();

            var withBody = withParameters.apply(fromSlice("body")).flatMap(Attribute::asNode).map(Compiler::renderTree)
                    .map(r -> r.mapValue(body -> withParameters.with(fromSlice("body"), new NodeAttribute(new Content(body)))))
                    .unwrapOrElse(Ok.apply(Some.apply(withParameters))).$()
                    .unwrapOrElse(withParameters);

            var withLines = withBody.apply(JavaString.fromSlice("lines")).flatMap(Attribute::asListOfNodes).map(lines -> lines.iter().map(Compiler::renderTree)
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectToResult(JavaList.asList()))
                    .map(value -> invert(value.mapValue(lines1 -> withBody.with(fromSlice("lines"), new NodeListAttribute(lines1)))))
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
            if (line.isEmpty()) {
                return Empty;
            } else {
                var withLines = lexTree(line).$();
                var transformed = new ClassTransformer(withLines)
                        .transform()
                        .unwrapOrElse(withLines);

                return renderTree(transformed).$();
            }
        }).mapErr(err -> new CompileException("Failed to compile line: '" + line.unwrap() + "'", err));
    }
}