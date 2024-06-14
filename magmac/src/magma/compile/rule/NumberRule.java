package magma.compile.rule;

public final class NumberRule extends FilterRule {
    public NumberRule(Rule child) {
        super(child);
    }

    @Override
    protected boolean filter(String input) {
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}
