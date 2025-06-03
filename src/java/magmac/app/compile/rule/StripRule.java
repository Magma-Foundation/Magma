package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

public record StripRule(String beforeKey, Rule rule, String afterKey) implements Rule {
    public StripRule(Rule rule) {
        this("", rule, "");
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.rule.lex(input.strip());
    }

    @Override
    public CompileResult<String> generate(Node node) {
        var before = node.findString(this.beforeKey).orElse("");
        var after = node.findString(this.afterKey).orElse("");
        return this.rule.generate(node).mapValue((String result) -> before + result + after);
    }
}
