package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;

public record TypeRule(String type, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.childRule.lex(input).mapValue(node -> node.retype(this.type));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (!node.is(this.type)) {
            return new Err<>(new CompileError("?", new StringContext("?")));
        }

        return this.childRule.generate(node);
    }
}
