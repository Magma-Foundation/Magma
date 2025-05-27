package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record StripRule(Rule rule) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return this.rule.lex(input.strip());
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.rule.generate(node);
    }
}
