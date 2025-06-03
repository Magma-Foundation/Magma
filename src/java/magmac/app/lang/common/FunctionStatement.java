package magmac.app.lang.common;

public class FunctionStatement<V> {
    protected final V child;

    public FunctionStatement(V child) {
        this.child = child;
    }

    public V child() {
        return this.child;
    }
}
