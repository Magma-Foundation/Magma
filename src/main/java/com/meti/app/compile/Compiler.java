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
import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.*;

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
                .collect(and(JavaList.asList()))).flatMap(withBody::withLines).unwrapOrElse(withBody);
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

        Set<? extends Renderer> renderers = JavaSet.of(new BlockRenderer(transformed),
                new DeclarationRenderer(transformed),
                new FunctionRenderer(transformed),
                new ImportRenderer(transformed));

        return renderers.iter()
                .map(Renderer::render)
                .flatMap(Iterators::fromOption)
                .head()
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElse(Err.apply(new CompileException("Failed to render: " + transformed)));
    }
}