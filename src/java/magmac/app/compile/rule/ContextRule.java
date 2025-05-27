package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.node.Node;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.error.CompileError;
import magmac.app.error.ImmutableCompileError;

import java.util.List;

public record ContextRule(String message, Rule rule) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        return this.rule.lex(input).result().mapErr(err -> new ImmutableCompileError(this.message, new StringContext(input), List.of(err)));
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.rule.generate(node).result().mapErr(err -> new ImmutableCompileError(this.message, new NodeContext(node), List.of(err)));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new CompileResult<>(this.lex0(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return new CompileResult<>(this.generate0(node));
    }
}
