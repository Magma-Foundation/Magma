package magmac.app;

import magmac.Rule;

import java.util.Optional;

public record InfixRule(Rule leftRule, String infix, Rule rightRule) implements Rule {
    @Override
    public Optional<MapNode> lex(String input) {
        int separator = input.lastIndexOf(this.infix());
        if (0 > separator) {
            return Optional.empty();
        }

        String left = input.substring(0, separator);
        String right = input.substring(separator + this.infix().length());
        return this.leftRule().lex(left).flatMap(leftResult -> {
            return this.rightRule().lex(right).map(rightResult -> {
                return leftResult.merge(rightResult);
            });
        });
    }
}