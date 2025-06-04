package magmac.app.lang.node;

/**
 * The kind of conditional statement, such as {@code if} or {@code while}.
 */
public enum ConditionalType {
    While("while"), If("if");

    private final String text;

    ConditionalType(String text) {
        this.text = text;
    }

    public String text() {
        return this.text;
    }
}
