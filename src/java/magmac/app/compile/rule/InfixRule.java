package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        int separator = input.lastIndexOf(this.infix());
        if (0 > separator) {
            return new Err<>(new CompileError("?", new StringContext("?")));
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