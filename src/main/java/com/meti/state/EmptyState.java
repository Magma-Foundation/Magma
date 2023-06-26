package com.meti.state;

import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;

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
        return None.apply();
    }

    @Override
    public PresentState withValue(NativeString value) {
        return new PresentState(value, stack);
    }
}