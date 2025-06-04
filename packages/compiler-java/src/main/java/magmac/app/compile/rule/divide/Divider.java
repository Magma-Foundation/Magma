package magmac.app.compile.rule.divide;

import magmac.api.iter.Iter;

/**
 * Splits an input string into subsections using a {@link DivideState}.
 */
public interface Divider {
    Iter<String> divide(String input);

    String createDelimiter();
}
