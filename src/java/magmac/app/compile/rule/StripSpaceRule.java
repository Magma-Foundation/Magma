package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

/**
 * Similar to {@link StripRule} but only trims spaces and tabs.
 * This avoids removing newlines that may be significant for some rules.
 */
public record StripSpaceRule(Rule rule) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return this.rule.lex(stripSpaces(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.rule.generate(node);
    }

    private static String stripSpaces(String input) {
        int start = 0;
        int end = input.length();
        while (start < end && isSpace(input.charAt(start))) {
            start++;
        }
        while (end > start && isSpace(input.charAt(end - 1))) {
            end--;
        }
        return input.substring(start, end);
    }

    private static boolean isSpace(char c) {
        return c == ' ' || c == '\t';
    }
}
