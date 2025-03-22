package magma;

import java.util.Optional;

public record PrefixRule(String prefix, StringRule compiler) implements StringRule {
    @Override
    public Optional<String> apply(String input) {
        return input.startsWith(prefix())
                ? compiler().apply(input.substring(prefix().length()))
                : Optional.empty();
    }
}