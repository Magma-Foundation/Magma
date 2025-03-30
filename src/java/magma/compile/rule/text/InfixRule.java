package magma.compile.rule.text;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.compile.rule.Rule;
import magma.compile.rule.locate.Locator;
import magma.result.Err;
import magma.result.Result;

public final class InfixRule implements Rule {
    private final Rule left;
    private final String infix;
    private final Rule right;
    private final Locator locator;

    public InfixRule(Rule left, String infix, Rule right, Locator locator) {
        this.left = left;
        this.infix = infix;
        this.right = right;
        this.locator = locator;
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return locator.locate(input, infix).map(index -> {
            String left = input.substring(0, index);
            String right = input.substring(index + infix.length());
            return this.left.parse(left).and(() -> this.right.parse(right)).mapValue(tuple -> tuple.left().merge(tuple.right()));
        }).orElseGet(() -> {
            return new Err<>(new CompileError("Infix '" + infix + "' not present", new StringContext(input)));
        });
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return left.generate(node).and(() -> right.generate(node)).mapValue(tuple -> tuple.left() + infix + tuple.right());
    }
}
