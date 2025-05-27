package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.Context;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;
import magmac.app.error.CompileError;

import java.util.List;

public record TypeRule(String type, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.childRule.lex(input)
                .mapValue(node -> node.retype(this.type))
                .mapErr(err -> this.createError(new StringContext(input), err));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(this.type)) {
            return this.childRule.generate(node).mapErr(err -> this.createError(new NodeContext(node), err));
        }

        return new Err<>(new CompileError("Type '" + this.type + "' not present", new NodeContext(node)));
    }

    private CompileError createError(Context context, CompileError err) {
        return new CompileError("Cannot use type '" + this.type + "'", context, List.of(err));
    }
}
