package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public Optional<Node> lex(String input) {
        if (!input.startsWith(this.prefix())) {
            return Optional.empty();
        }
        String sliced = input.substring(this.prefix().length());
        return this.childRule().lex(sliced);
    }
}