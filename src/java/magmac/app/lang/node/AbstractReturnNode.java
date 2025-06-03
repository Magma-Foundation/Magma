package magmac.app.lang.node;

public class AbstractReturnNode<V> {
    protected final V child;

    public AbstractReturnNode(V child) {
        this.child = child;
    }

    public V child() {
        return child;
    }
}
