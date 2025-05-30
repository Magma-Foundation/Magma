package magmac.app.compile.rule.filter;

public class SymbolFilter implements Filter {
    private static boolean isValidSymbolChar(char c, int index) {
        return Character.isLetter(c) || (0 != index && Character.isDigit(c)) || '_' == c;
    }

    @Override
    public boolean test(String input) {
        int length = input.length();
        int i = 0;
        while (i < length) {
            char c = input.charAt(i);
            if (SymbolFilter.isValidSymbolChar(c, i)) {
                i++;
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