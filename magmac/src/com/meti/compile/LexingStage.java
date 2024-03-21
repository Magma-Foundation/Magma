package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.result.ExceptionalStream;
import com.meti.collect.result.Result;
import com.meti.collect.result.Results;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.compile.java.JavaLexer;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeAttribute;
import com.meti.java.JavaString;

import static com.meti.collect.result.Err.Err;
import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public class LexingStage {
    public static Result<JavaList<Node>, CompileException> lexExpression(JavaString line, int indent) {
        return new JavaLexer(line, indent).lex()
                .map(LexingStage::lexTree)
                .into(ExceptionalStream::new)
                .mapInner(JavaList::stream)
                .flatMap(Results::invertStream)
                .collect(Collectors.exceptionally(Collectors.toList()));
    }

    static Result<JavaList<Node>, CompileException> lexContent(Node content) {
        if (content.is(Content.Id)) {
            return $Result(() -> {
                var value = content.apply("value").flatMap(Attribute::asString).into(ThrowableOption::new).orElseThrow(() -> new CompileException("No value present on content.")).$();
                var indent = content.apply("indent").flatMap(Attribute::asInteger).into(ThrowableOption::new).orElseThrow(() -> new CompileException("No indent present on content.")).$();
                return lexExpression(value, indent).$();
            });
        } else {
            return Err(new CompileException("Not content: '" + content));
        }
    }

    static Result<JavaList<JavaList<Node>>, CompileException> lexContentList(JavaList<? extends Node> content) {
        return content.stream().map(LexingStage::lexContent).into(ExceptionalStream::new).foldRightFromInitialExceptionally(new JavaList<>(), (javaListJavaList, nodeJavaList) -> javaListJavaList.stream().cross(nodeJavaList::stream).map(pair -> pair.left().add(pair.right())).collect(Collectors.toList()));
    }

    public static Result<JavaList<Node>, CompileException> lexTree(Node node) {
        return node.apply("value")
                .flatMap(Attribute::asNode)
                .map(LexingStage::lexContent)
                .map(result -> result.mapValue(values1 -> values1.stream()
                        .map(NodeAttribute::new)
                        .map(value -> node.with("value", value).orElse(node))
                        .collect(Collectors.toList())))
                .orElse(Ok(JavaList.from(node)));
    }
}
