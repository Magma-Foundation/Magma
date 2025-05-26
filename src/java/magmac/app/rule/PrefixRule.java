package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    private Optional<Node> lex0(String input) {
        if (!input.startsWith(this.prefix())) {
            return Optional.empty();
        }
        String sliced = input.substring(this.prefix().length());
        return this.childRule().lex(sliced).optional();
    }

    @Override
    public RuleResult lex(String input) {
        return new RuleResult(lex0(input));
    }
}