package magmac.app.lang.node;

/**
 * Enumerates the supported postfix operators.
 */
public enum PostVariant {
    Increment("post-increment"),
    Decrement("post-decrement");

    private final String type;

    PostVariant(String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }
}
