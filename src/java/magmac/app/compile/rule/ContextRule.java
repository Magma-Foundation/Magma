package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;
import magmac.app.error.CompileError;

import java.util.List;

public record ContextRule(String message, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.rule.lex(input).mapErr(err -> new CompileError(this.message, new StringContext(input), List.of(err)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.rule.generate(node).mapErr(err -> new CompileError(this.message, new NodeContext(node), List.of(err)));
    }
}
