package magmac.app.lang.node;

/**
 * Represents an {@code if} or {@code while} conditional. The {@code type}
 * indicates which construct is used and {@code condition} stores the
 * expression, for example {@code if (x > 0)}.
 */
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
