package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.compile.node.Node;

public record TypeRule(String type, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.childRule.lex(input).mapValue(node -> node.retype(this.type));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(this.type)) {
            return this.childRule.generate(node);
        }

        return new Err<>(new CompileError("Type '" + this.type + "' not present", new NodeContext(node)));
    }
}
