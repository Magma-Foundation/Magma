package magma.compile.rule;

public final class NumberRule extends FilterRule {
    public NumberRule(Rule child) {
        super(child);
    }

    private static boolean allDigits(String input) {
        if (input.isEmpty()) return false;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected String computeMessage() {
        return "Not a number.";
    }

    @Override
    protected boolean filter(String input) {
        return input.startsWith("-")
                ? allDigits(input.substring(1))
                : allDigits(input);
    }
}
