package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    private Optional<Node> lex0(String input) {
        if (!input.endsWith(this.suffix())) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - this.suffix().length());
        return this.childRule().lex(slice).optional();
    }

    @Override
    public RuleResult lex(String input) {
        return new RuleResult(lex0(input));
    }
}