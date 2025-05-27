package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.node.Node;

public record StripRule(Rule rule) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        return this.rule.lex(input.strip()).result();
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.rule.generate(node).result();
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
