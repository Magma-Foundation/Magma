package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    private Optional<Node> lex0(String input) {
        int separator = input.lastIndexOf(this.infix());
        if (0 > separator) {
            return Optional.empty();
        }

        String left = input.substring(0, separator);
        String right = input.substring(separator + this.infix().length());
        return this.leftRule().lex(left).optional().flatMap(leftResult -> {
            return this.rightRule().lex(right).optional().map(rightResult -> {
                return leftResult.merge(rightResult);
            });
        });
    }

    @Override
    public RuleResult lex(String input) {
        return new RuleResult(lex0(input));
    }
}