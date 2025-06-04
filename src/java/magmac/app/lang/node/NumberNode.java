package magmac.app.lang.node;

/**
 * Simple wrapper for a numeric literal like {@code 42}.
 */
public class NumberNode {
    private final String value;

    public NumberNode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
