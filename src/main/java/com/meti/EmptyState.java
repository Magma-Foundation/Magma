package com.meti;

import java.util.HashMap;
import java.util.function.Function;

public class EmptyState extends State {

    public EmptyState(Stack stack) {
        super(stack);
    }

    private EmptyState() {
        this(new Stack());
    }

    public static State create() {
        return new EmptyState();
    }

    @Override
    protected State copy(Stack stack) {
        return new EmptyState(stack);
    }

    @Override
    public State mapValue(Function<NativeString, NativeString> mapper) {
        return this;
    }

    @Override
    public Option<NativeString> findValue() {
        return new None<>();
    }

    @Override
    public PresentState withValue(NativeString value) {
        return new PresentState(value, new Stack(new SafeMap(stack.definitions().unwrap())));
    }
}
