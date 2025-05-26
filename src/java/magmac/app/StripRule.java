package magmac.app;

import java.util.Optional;

public record StripRule(Rule rule) implements Rule {
    @Override
    public Optional<MapNode> lex(String input) {
        return this.rule.lex(input.strip());
    }
}
