package magmac.app.lang.common;

public class Access<T, V> {
    private final T type;
    private final V receiver;
    private final String property;

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
