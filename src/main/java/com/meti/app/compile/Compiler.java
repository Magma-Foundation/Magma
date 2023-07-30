package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.app.NodeListAttribute;
import com.meti.app.compile.block.BlockRenderer;
import com.meti.app.compile.clazz.ClassTransformer;
import com.meti.app.compile.clazz.ObjectRenderer;
import com.meti.app.compile.clazz.StaticTransformer;
import com.meti.app.compile.clazz.Transformer;
import com.meti.app.compile.declare.DeclarationRenderer;
import com.meti.app.compile.function.FunctionRenderer;
import com.meti.app.compile.imports.ImportRenderer;
import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.core.Some;
import com.meti.iterate.Iterator;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaString;
import com.meti.java.Set;
import com.meti.java.String_;

import static com.meti.core.Results.$Result;
import static com.meti.core.Results.invert;
import static com.meti.java.JavaList.intoList;
import static com.meti.java.JavaSet.fromSet;
import static com.meti.java.JavaSet.of;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {

    private static Result<String_, CompileException> renderTree(Node transformed, int depth) {
        return $Result(CompileException.class, () -> {
            var withParameters = transformed.applyOptionally(fromSlice("parameters")).flatMap(Attribute::asSetOfNodes).map(lines -> lines.iter().map(node -> renderTree(node, depth + 1))
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectAsResult(fromSet()))
                    .map(value -> invert(value.mapValue(parameters -> transformed.withOptionally(fromSlice("parameters"), new NodeSetAttribute(parameters)))))
                    .flatMap(value -> value)
                    .unwrapOrElse(Ok.apply(transformed))
                    .$();

            var withBody = withParameters.applyOptionally(fromSlice("body")).flatMap(Attribute::asNode).map(node -> renderTree(node, depth + 1))
                    .map(r -> r.mapValue(body -> withParameters.withOptionally(fromSlice("body"), new NodeAttribute(new Content(body)))))
                    .unwrapOrElse(Ok.apply(Some.apply(withParameters))).$()
                    .unwrapOrElse(withParameters);

            var withLines = withBody.applyOptionally(JavaString.fromSlice("lines"))
                    .flatMap(Attribute::asListOfNodes)
                    .map(lines -> lines.iter().map(node -> renderTree(node, depth + 1))
                            .into(ResultIterator::new)
                            .mapToResult(Content::new)
                            .collectAsResult(intoList()))
                    .map(value -> invert(value.mapValue(lines1 -> withBody.withOptionally(fromSlice("lines"), new NodeListAttribute(lines1)))))
                    .flatMap(value -> value)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();

            return renderNode(withLines, depth).$();
        });
    }

    private static Result<String_, CompileException> renderNode(Node node, int depth) {
        Set<? extends Renderer> renderers = of(
                new ObjectRenderer(node),
                new BlockRenderer(node, depth),
                new DeclarationRenderer(node),
                new FunctionRenderer(node),
                new ImportRenderer(node),
                new ContentRenderer(node));

        return renderers.iter()
                .map(Renderer::render)
                .flatMap(Iterators::fromOption)
                .head()
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElse(Err.apply(new CompileException("Failed to render: " + node)));
    }

    public Result<String_, CompileException> compile() {
        return new Splitter(input).split()
                .filter(line -> !line.strip().startsWith("package "))
                .map(this::compileNode)
                .into(ResultIterator::new)
                .collectAsResult(joining(Empty))
                .mapValue(inner -> inner.unwrapOrElse(Empty));
    }

    private Result<String_, CompileException> compileNode(String_ line) {
        return $Result(CompileException.class, () -> {
            if (line.isEmpty()) {
                return Empty;
            } else {
                var root = new LexingStage(line).lexTree().$();

                while (true) {
                    Iterator<? extends Transformer> transformers = Iterators.of(new StaticTransformer(root),
                            new ClassTransformer(root));

                    var transformed = transformers
                            .map(Transformer::transform)
                            .flatMap(Iterators::fromOption)
                            .head().toTuple(new MapNode(fromSlice("")));
                    if (transformed.a()) {
                        root = transformed.b();
                    } else {
                        break;
                    }
                }

                return renderTree(root, 0).$();
            }
        }).mapErr(err -> new CompileException("Failed to compile input: '" + line.unwrap() + "'", err));
    }
}