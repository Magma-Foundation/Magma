package magmac.app.compile.rule.filter;

public class SymbolFilter implements Filter {
    private static boolean isValidSymbolChar(char c, int index) {
        return Character.isLetter(c) || (0 != index && Character.isDigit(c)) || '_' == c;
    }

    @Override
    public boolean test(String input) {
        var length = input.length();
        if (0 == length) {
            return false;
        }

        var i = 0;
        while (i < length) {
            var c = input.charAt(i);
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