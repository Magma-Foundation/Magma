package magma.compile.rule.tree;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.compile.context.StringContext;
import magma.compile.rule.Rule;
import magma.compile.rule.divide.Divider;
import magma.compile.rule.text.StringRule;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public record NodeListRule(String propertyKey, Divider divider, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        List_<String> segments = divider.divide(input);
        if (segments.isEmpty()) return new Ok<>(new MapNode());

        return segments.stream()
                .<List_<Node>, CompileError>foldToResult(Lists.empty(), (children, element) -> childRule().parse(element).mapValue(children::add))
                .mapValue(children -> new MapNode().withNodeList(propertyKey(), children))
                .mapErr(err -> new CompileError("Failed to attach node list '" + propertyKey + "'", new StringContext(input), Lists.of(err)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(propertyKey)
                .map(this::generateChildren)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(node))))
                .mapErr(err -> new CompileError("Failed to generate node list '" + propertyKey + "'", new NodeContext(node), Lists.of(err)));
    }

    private Result<String, CompileError> generateChildren(List_<Node> children) {
        return children.stream().foldToResult("", (current, element) -> childRule
                .generate(element)
                .mapValue(result -> divider.join(current, result)));
    }
}