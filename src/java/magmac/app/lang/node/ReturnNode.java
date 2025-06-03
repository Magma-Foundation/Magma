package magmac.app.lang.node;

public class ReturnNode<V> {
    protected final V child;

    public ReturnNode(V child) {
        this.child = child;
    }

    public V child() {
        return child;
    }
}
