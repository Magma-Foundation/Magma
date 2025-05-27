package magmac.app.compile.rule;

public class SymbolFilter implements Filter {
    @Override
    public boolean test(String input) {
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c) || (0 != i && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Override
    public String createMessage() {
        return "Not a symbol";
    }
}