package magmac.app.lang.common;

public class AbstractFunctionStatement<V> {
    protected final V child;

    public AbstractFunctionStatement(V child) {
        this.child = child;
    }

    public V child() {
        return this.child;
    }
}
