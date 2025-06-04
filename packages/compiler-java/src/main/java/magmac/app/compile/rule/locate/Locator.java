package magmac.app.compile.rule.locate;

import magmac.api.Option;

/**
 * Finds positions within a string during lexing.
 */
public interface Locator {
    Option<Integer> locate(String input, String infix);
}
