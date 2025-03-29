package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.result.Err;
import magma.result.Result;

public record TypeRule(String type, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return rule.parse(input).mapValue(node -> node.withType(type));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(type)) {
            return rule.generate(node);
        }

        return new Err<>(new CompileError("Node was not of type '" + type + "'", new NodeContext(node)));
    }
}
