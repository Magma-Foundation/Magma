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
import static com.meti.iterate.Collectors.and;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.joining;

public record Compiler(String_ input) {

    public static String_ resolveType(String_ type) {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .applyOptionally(type.unwrap())
                .map(JavaString::fromSlice)
                .unwrapOrElse(type);
    }

    private static Node lexNode(String_ line) {
        return new ImportLexer(line).lex()
                .or(new ClassLexer(line).lex())
                .or(new BlockLexer(line).lex())
                .or(new FunctionLexer(line).lex())
                .or(new DeclarationLexer(line).lex())
                .unwrapOrElse(Content.ofContent(line));
    }

    private static Node lexTree(String_ line) {
        var node = lexNode(line);

        var withBody = node.body().flatMap(Node::value)
                .map(Compiler::lexTree)
                .flatMap(node::withBody)
                .unwrapOrElse(node);

        return withBody.lines().flatMap(lines -> lines.iter()
                        .map(Node::value)
                        .map(value -> value.map(Compiler::lexTree))
                        .collect(and(JavaList.asList())))
                .flatMap(withBody::withLines)
                .unwrapOrElse(withBody);
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
        var withLines = lexTree(line);
        var transformed = new ClassTransformer(withLines)
                .transform()
                .unwrapOrElse(withLines);

        return renderTree(transformed);
    }
}