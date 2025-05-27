package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.node.Node;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.CompileError;
import magmac.app.error.ImmutableCompileError;

import java.util.List;

public record ContextRule(String message, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.rule.lex(input).mapErr(err -> new ImmutableCompileError(this.message, new StringContext(input), List.of(err)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.rule.generate(node).mapErr(err -> new ImmutableCompileError(this.message, new NodeContext(node), List.of(err)));
    }
}
