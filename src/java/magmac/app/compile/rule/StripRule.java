package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.node.Node;

public record StripRule(Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.rule.lex(input.strip());
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.rule.generate(node);
    }
}
