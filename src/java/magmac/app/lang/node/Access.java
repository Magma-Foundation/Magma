package magmac.app.lang.node;

public class Access<T, V> {
    protected final T type;
    protected final V receiver;
    protected final String property;

    public Access(T type, V receiver, String property) {
        this.type = type;
        this.receiver = receiver;
        this.property = property;
    }

    public T type() {
        return type;
    }

    public V receiver() {
        return receiver;
    }

    public String property() {
        return property;
    }
}
