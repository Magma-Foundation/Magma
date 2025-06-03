package magmac.app.lang.java;

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
