package magmac.app.lang.node;

/**
 * Base node for statements that return a value such as {@code return x;} or
 * {@code yield x;}. The {@code child} stores the returned expression.
 */
public class AbstractReturnNode<V> {
    protected final V child;

    public AbstractReturnNode(V child) {
        this.child = child;
    }

    public V child() {
        return child;
    }
}
