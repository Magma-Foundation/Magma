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
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }

        return true;
    }
}
