package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.result.ExceptionalStream;
import com.meti.collect.result.Result;
import com.meti.collect.result.Results;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.compile.java.JavaLexer;
import com.meti.compile.node.*;
import com.meti.java.JavaString;
import org.jetbrains.annotations.NotNull;

import static com.meti.collect.result.Err.Err;
import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public class LexingStage {
    private static JavaList<Node> applyChoicesToNode(Node root, JavaList<JavaList<Node>> initialChoices) {
        return crossCombinations(initialChoices)
                .stream()
                .map(combination -> root.with("children", new NodeListAttribute(combination)).orElse(root))
                .collect(Collectors.toList());
    }

    private static JavaList<JavaList<Node>> crossCombinations(JavaList<JavaList<Node>> list) {
        return list.stream()
                .foldRight(LexingStage::wrapEachChildAsInitialCombinations, LexingStage::crossChildrenCombinations)
                .orElse(JavaList.empty());
    }

    private static JavaList<JavaList<Node>> wrapEachChildAsInitialCombinations(JavaList<Node> first) {
        return first.stream().map(JavaList::from).collect(Collectors.toList());
    }

    private static JavaList<JavaList<Node>> crossChildrenCombinations(JavaList<JavaList<Node>> previousChildrenCombinations, JavaList<Node> childOptions) {
        return previousChildrenCombinations.stream().cross(childOptions::stream)
                .map(pair -> pair.left().add(pair.right()))
                .collect(Collectors.toList());
    }

    public Result<JavaList<Node>, CompileException> lexTree(Node node) {
        return withNode(node).flatMapValue(this::withChildrenForNodes);
    }

    private Result<JavaList<Node>, CompileException> withChildrenForNodes(JavaList<Node> withNodes) {
        return withNodes.stream().map(this::withChildrenForNode).into(ExceptionalStream::new)
                .mapInner(JavaList::stream)
                .flatMap(Results::invertStream)
                .collect(Collectors.exceptionally(Collectors.toList()));
    }

    private Result<JavaList<Node>, CompileException> withChildrenForNode(Node withNode) {
        return withNode.apply("children")
                .flatMap(Attribute::asListOfNodes)
                .map(this::lexListOfContent)
                .map(result -> result.mapValue(list -> applyChoicesToNode(withNode, list)))
                .orElse(Ok(JavaList.from(withNode)));
    }

    private Result<JavaList<JavaList<Node>>, CompileException> lexListOfContent(JavaList<? extends Node> children) {
        return children.stream()
                .map(this::lexContent)
                .into(ExceptionalStream::new)
                .collectExceptionally(Collectors.toList());
    }

    private Result<JavaList<Node>, CompileException> withNode(Node node) {
        return node.apply("value")
                .flatMap(Attribute::asNode)
                .map(this::lexContent)
                .map(result -> result.mapValue(values1 -> applyValuesToNode(node, values1)))
                .orElse(Ok(JavaList.from(node)));
    }

    private static JavaList<Node> applyValuesToNode(Node node, JavaList<Node> values) {
        return values.stream()
                .map(NodeAttribute::new)
                .map(value -> node.with("value", value).orElse(node))
                .collect(Collectors.toList());
    }

    Result<JavaList<Node>, CompileException> lexContent(Node content) {
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

    public Result<JavaList<Node>, CompileException> lexExpression(JavaString line1, int indent1) {
        return createRootLexer(line1, indent1).lex()
                .map(node -> new LexingStage().lexTree(node))
                .into(ExceptionalStream::new)
                .mapInner(JavaList::stream)
                .flatMap(Results::invertStream)
                .collect(Collectors.exceptionally(Collectors.toList()));
    }

    @NotNull
    JavaLexer createRootLexer(JavaString line1, int indent1) {
        return new JavaLexer(line1, indent1);
    }
}