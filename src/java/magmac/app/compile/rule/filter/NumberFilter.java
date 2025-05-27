package magmac.app.compile.rule.filter;

public class NumberFilter implements Filter {
    @Override
    public boolean test(String input) {
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public String createMessage() {
        return "Not a number";
    }
}