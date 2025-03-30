package magma.compile.lang;

import magma.compile.rule.locate.Locator;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

class TypeSeparatorLocator implements Locator {
    @Override
    public Option<Integer> locate(String input, String infix) {
        int depth = 0;
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) return new Some<>(i);
            if (c == '>') depth++;
            if (c == '<') depth--;
        }
        return new None<>();
    }
}
