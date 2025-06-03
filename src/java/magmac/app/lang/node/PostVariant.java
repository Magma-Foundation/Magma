package magmac.app.lang.node;

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
