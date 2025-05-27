package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.node.Node;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.CompileError;
import magmac.app.error.ImmutableCompileError;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        int separator = input.indexOf(this.infix());
        if (0 > separator) {
            return new Err<>(new ImmutableCompileError("Infix '" + this.infix + "' not present", new StringContext(input)));
        }

        String left = input.substring(0, separator);
        String right = input.substring(separator + this.infix().length());
        return this.leftRule.lex(left)
                .and(() -> this.rightRule.lex(right))
                .mapValue(tuple -> tuple.left().merge(tuple.right()));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.leftRule.generate(node)
                .and(() -> this.rightRule.generate(node))
                .mapValue(tuple -> tuple.left() + this.infix + tuple.right());
    }
}