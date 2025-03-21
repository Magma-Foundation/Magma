package magma;

import java.util.Optional;

class TypeSeparatorLocator implements Locator {
    @Override
    public Optional<Integer> locate(String input, String slice) {
        var depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            final var c = input.charAt(i);
            if (c == ' ' && depth == 0) return Optional.of(i);
            if (c == '>') depth++;
            if (c == '<') depth--;
        }
        return Optional.empty();
    }
}
