package magma.compile.rule;

public final class SymbolRule extends FilterRule {
    public SymbolRule(Rule child) {
        super(child);
    }

    @Override
    protected String computeMessage() {
        return "Not a symbol.";
    }

    @Override
    protected boolean filter(String input) {
        int i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || isUnderscore(c) || isValidDigit(i, c)) {
                i++;
                continue;
            }

            return false;
        }

        return true;
    }

    private static boolean isValidDigit(int i, char c) {
        return i != 0 && Character.isDigit(c);
    }

    private static boolean isUnderscore(char c) {
        return c == '_';
    }
}
