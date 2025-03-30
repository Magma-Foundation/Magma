package magma.compile.lang;

public class NumberFilter implements Filter {
    @Override
    public boolean isValid(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) continue;
            return false;
        }

        return true;
    }
}