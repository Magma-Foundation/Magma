package magmac.app.lang.java;

/**
 * Base class for something that can be called with arguments. In the source
 * syntax this looks like {@code doStuff(5, "first")} where {@code doStuff}
 * would be the caller and the values are the arguments.
 */

import magmac.api.collect.list.List;

public abstract class Invokable<C, A> {
    protected final C caller;
    protected final List<A> arguments;

    protected Invokable(C caller, List<A> arguments) {
        this.caller = caller;
        this.arguments = arguments;
    }

    public C caller() {
        return this.caller;
    }

    public List<A> arguments() {
        return this.arguments;
    }
}
