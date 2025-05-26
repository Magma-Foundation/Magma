package magmac.app;

import java.util.Optional;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Optional<MapNode> lex(String input) {
        if (!input.endsWith(this.suffix())) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - this.suffix().length());
        return this.childRule().lex(slice);
    }
}