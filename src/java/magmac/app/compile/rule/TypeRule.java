package magmac.app.compile.rule;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.Context;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public record TypeRule(String type, Rule childRule) implements Rule {
    private CompileError createError(Context context, CompileError err) {
        return new ImmutableCompileError("Cannot use type '" + this.type + "'", context, Lists.of(err));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.childRule.lex(input)
                .mapValue((Node node) -> node.retype(this.type))
                .mapErr((CompileError err) -> this.createError(new StringContext(input), err));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        if (node.is(this.type)) {
            return this.childRule.generate(node)
                    .mapErr((CompileError err) -> this.createError(new NodeContext(node), err));
        }

        return CompileErrors.createNodeError("Type '" + this.type + "' not present", node);
    }
}
