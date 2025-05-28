package magmac.app.compile.rule.filter;

public class NumberFilter implements Filter {
    @Override
    public boolean test(String input) {
        int length = input.length();
        int i = 0;
        while (i < length) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                i++;
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