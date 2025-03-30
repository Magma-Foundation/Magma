package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Result;

public record InfixRule(Rule left, String infix, Rule right) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        int index = input.indexOf(infix);
        if (index >= 0) {
            String left = input.substring(0, index);
            String right = input.substring(index + infix.length());
            return this.left.parse(left).and(() -> this.right.parse(right)).mapValue(tuple -> tuple.left().merge(tuple.right()));
        } else {
            return new Err<>(new CompileError("Infix '" + infix + "' not present", new StringContext(input)));
        }
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return left.generate(node).and(() -> right.generate(node)).mapValue(tuple -> tuple.left() + infix + tuple.right());
    }
}
