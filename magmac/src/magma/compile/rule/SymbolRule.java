package magma.compile.rule;

public final class SymbolRule extends FilterRule {
    public SymbolRule(Rule child) {
        super(child);
    }

    @Override
    protected boolean filter(String input) {
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (!Character.isLetter(c) || c == '_') {
                return false;
            }
        }

        return true;
    }
}
