package magma.compile.rule.tree;

import jvm.collect.list.Lists;
import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.compile.context.StringContext;
import magma.compile.rule.Rule;
import magma.result.Err;
import magma.result.Result;

public record NodeRule(String propertyKey, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return childRule.parse(input)
                .mapValue(node -> new MapNode().withNode(propertyKey, node))
                .mapErr(err -> new CompileError("Failed to attach node '" + propertyKey + "'", new StringContext(input), Lists.of(err)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNode(propertyKey)
                .map(childRule::generate)
                .orElseGet(() -> new Err<>(new CompileError("Node '" + propertyKey + "' not present", new NodeContext(node))));
    }
}
