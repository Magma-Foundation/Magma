package magmac.app.compile.rule.filter;

/**
 * Predicate used by a lexer to ignore or accept tokens.
 */
public interface Filter {
    boolean test(String input);

    String createMessage();
}
