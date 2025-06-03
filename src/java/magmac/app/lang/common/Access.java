package magmac.app.lang.common;

import magmac.api.Option;
import magmac.api.collect.list.List;

public class Access<V, R, T> {
    private final V variant;
    private final R receiver;
    private final String property;
    private final Option<List<T>> maybeArguments;

    public Access(V variant, R receiver, String property, Option<List<T>> maybeArguments) {
        this.variant = variant;
        this.receiver = receiver;
        this.property = property;
        this.maybeArguments = maybeArguments;
    }

    public V type() {
        return this.variant;
    }

    public R receiver() {
        return this.receiver;
    }

    public String property() {
        return this.property;
    }
}
