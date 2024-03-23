package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.Pair;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.result.ExceptionalStream;
import com.meti.collect.result.Result;
import com.meti.collect.result.Results;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.node.*;
import com.meti.compile.scope.BlockNode;
import com.meti.java.JavaString;

import java.util.ArrayList;
import java.util.List;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;
import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public class TransformingStage {
    static Result<Node, CompileException> transformAST(Node root) {
        return $Result(() -> {
            var transformed = transformNode(root).$();
            var withValues = transformed.streamPairs(NodeAttribute.Type).map(tuple -> tuple.mapRight(Attribute::asNode)).map(tuple -> tuple.into(Pair::requireRight)).flatMap(Streams::fromOption).map(tuple -> tuple.mapRight(TransformingStage::transformAST)).map(Results::invertTupleByRight).into(ExceptionalStream::new).foldRightFromInitialExceptionally(transformed, (node, javaStringNodeTuple) -> node.with(javaStringNodeTuple.left().inner(), new NodeAttribute(javaStringNodeTuple.right())).orElse(node)).$();

            return withValues.apply("children").flatMap(Attribute::asListOfNodes).<List<? extends Node>>map(JavaList::unwrap).map(children -> Streams.fromNativeList(children).map(TransformingStage::transformAST).collect(Collectors.exceptionally(Collectors.toList()))).into(Results::invertOption).$().flatMap(children -> withValues.with("children", new NodeListAttribute(children))).orElse(withValues);
        });
    }

    public static Option<Result<Node, CompileException>> transformBlock(Node node) {
        if (!node.is("block")) return None.None();

        var realIndent = node.apply("indent").flatMap(Attribute::asInteger).orElse(0) + 1;
        var children = node.apply("children").flatMap(Attribute::asListOfNodes).orElse(JavaList.empty());

        var newChildren = children.stream().map(child -> {
            if (child.is("implementation") || child.is("try") || child.is("catch")) {
                return MapNode.Builder(JavaString.from("statement"))
                        .withNode("value", child)
                        .withInteger("indent", realIndent)
                        .complete();
            } else {
                return MapNode.Builder(JavaString.from("terminating-statement"))
                        .withNode("value", child)
                        .withInteger("indent", realIndent)
                        .complete();
            }
        }).collect(Collectors.toList());

        return Some(Ok(node.with("children", new NodeListAttribute(newChildren)).orElse(node)));
    }

    public static Result<Node, CompileException> transformNode(Node node) {
        return transformBlock(node).or(() -> transformClass(node)).orElse(Ok(node));
    }

    public static Option<Result<Node, CompileException>> transformClass(Node node) {
        return $Option(() -> {
            if (!node.is("class")) return $$();

            var flags = node.apply("flags").$().asListOfStrings().$();
            var name = node.apply("name").$().asString().$();

            var outputFlags = new JavaList<JavaString>();
            if (flags.contains(JavaString.from("public"))) {
                outputFlags = JavaList.from(JavaString.from("export"));
            }

            var children = new JavaList<Node>(new ArrayList<>(node.apply("body")
                    .flatMap(Attribute::asNode)
                    .flatMap(value -> value.apply("children"))
                    .flatMap(Attribute::asListOfNodes)
                    .$()
                    .unwrap()));

            var newChildren = node.apply("extends").flatMap(Attribute::asString).map(extendsValue -> children.add(MapNode.Builder(JavaString.from("with")).withString("type", extendsValue).complete())).orElse(children);

            var contentOutput = new BlockNode(0, newChildren);

            return Ok(MapNode.Builder(JavaString.from("object")).withListOfStrings("flags", outputFlags).withString("name", name).withNode("body", contentOutput).complete());
        });
    }
}
