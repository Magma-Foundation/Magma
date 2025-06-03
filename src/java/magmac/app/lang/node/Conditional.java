package magmac.app.lang.node;

public class Conditional<V> {
    protected final ConditionalType type;
    protected final V condition;

    public Conditional(ConditionalType type, V condition) {
        this.type = type;
        this.condition = condition;
    }

    public ConditionalType type() {
        return type;
    }

    public V condition() {
        return condition;
    }
}
