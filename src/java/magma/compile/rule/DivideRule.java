package magma.compile.rule;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.compile.rule.divide.Divider;
import magma.result.Err;
import magma.result.Result;

public record DivideRule(Divider divider, Rule childRule, String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return divider.divide(input)
                .stream()
                .<List_<Node>, CompileError>foldToResult(Lists.empty(), (children, element) -> childRule().parse(element).mapValue(children::add))
                .mapValue(children -> new MapNode().withNodeList(propertyKey(), children));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(propertyKey)
                .map(this::generateChildren)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(node))));
    }

    private Result<String, CompileError> generateChildren(List_<Node> children) {
        return children.stream().foldToResult("", (current, element) -> childRule
                .generate(element)
                .mapValue(result -> divider.join(current, result)));
    }
}