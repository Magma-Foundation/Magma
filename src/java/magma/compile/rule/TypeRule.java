package magma.compile.rule;

import jvm.collect.list.Lists;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Result;

public record TypeRule(String type, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return rule.parse(input)
                .mapValue(node -> node.retype(type))
                .mapErr(error -> new CompileError("Failed to assign type", new StringContext(input), Lists.of(error)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(type)) {
            return rule.generate(node);
        }

        return new Err<>(new CompileError("Node was not of type '" + type + "'", new NodeContext(node)));
    }
}
