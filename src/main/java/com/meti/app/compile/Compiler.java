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
import com.meti.core.*;
import com.meti.iterate.Iterator;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.*;

import static com.meti.core.Results.$Result;
import static com.meti.core.Results.invert;
import static com.meti.java.JavaList.intoList;
import static com.meti.java.JavaSet.fromSet;
import static com.meti.java.JavaSet.of;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {
    private static Result<Node, CompileException> lexTree(String_ line) {
        if (line.isEmpty()) {
            return Err.apply(new CompileException("Input cannot be empty."));
        }

        return $Result(CompileException.class, () -> {
            var node = new JavaLexer(line).lex().unwrapOrElse(Ok.apply(new Content(line))).$();
            var withNodeLists = attachNodeLists(node).$();
            return attachNodes(withNodeLists).$();
        }).mapErr(err -> new CompileException("Failed to lex line: " + line.unwrap(), err));
    }

    private static Result<Node, CompileException> attachNodes(Node withNodeLists) {
        return withNodeLists.ofGroup(Node.Group.NodeList)
                .map(key -> lexNodeAttribute(key, withNodeLists))
                .into(ResultIterator::new)
                .foldLeftInner(withNodeLists, (accumulated, element) -> accumulated.with(element.a(), element.b()));
    }

    private static Result<Node, CompileException> attachNodeLists(Node node) {
        return node.ofGroup(Node.Group.NodeList).map(key -> lexNodeList(node, key))
                .into(ResultIterator::new)
                .foldLeftInner(node, (accumulated, element) -> accumulated.with(element.a(), element.b()));
    }

    private static Result<Tuple<Key<String_>, NodeListAttribute>, CompileException> lexNodeList(Node node, Key<String_> key) {
        return node.apply(key)
                .asListOfNodes()
                .map(list -> lexNodeList1(key, list))
                .unwrapOrElseGet(() -> createInvalid(key, "set of nodes"));
    }

    private static <T> Result<T, CompileException> createInvalid(Key<String_> key, String type) {
        var format = "Key '%s' was not a %s.";
        var message = format.formatted(key, type);
        return Err.apply(new CompileException(message));
    }

    private static Result<Tuple<Key<String_>, NodeAttribute>, CompileException> lexNodeAttribute(Key<String_> key, Node withNodeLists) {
        return withNodeLists.apply(key).asNode().map(list -> lexUnwrapped(list)
                .mapValue(NodeAttribute::new)
                .mapValue(value -> new Tuple<>(key, value))).unwrapOrElseGet(() -> createInvalid(key, "node"));
    }

    private static Result<Tuple<Key<String_>, NodeListAttribute>, CompileException> lexNodeList1(Key<String_> key, List<? extends Node> list) {
        return list.iter()
                .map(Compiler::lexUnwrapped)
                .into(ResultIterator::new)
                .collectAsResult(intoList())
                .mapValue(NodeListAttribute::new)
                .mapValue(value -> new Tuple<>(key, value));
    }

    private static Result<Node, CompileException> lexUnwrapped(Node node1) {
        return node1.applyOptionally(fromSlice("value")).flatMap(Attribute::asString)
                .map(Compiler::lexTree)
                .unwrapOrElse(Err.apply(new CompileException("No value present in list.")));
    }

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
                var root = lexTree(line).$();

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
        }).mapErr(err -> new CompileException("Failed to compile line: '" + line.unwrap() + "'", err));
    }
}