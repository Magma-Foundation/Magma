package magmac.app.lang.node;

public final class ArrayType<T> {
    private final T type;

    public ArrayType(T type) {
        this.type = type;
    }

    public T child() {
        return this.type;
    }
}