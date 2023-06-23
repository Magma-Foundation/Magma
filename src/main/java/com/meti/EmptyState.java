package com.meti;

import java.util.HashMap;
import java.util.Map;

public class EmptyState extends State {

    public EmptyState(Map<NativeString, NativeString> declarations) {
        super(new Stack(new SafeMap(declarations)));
    }

    private EmptyState() {
        this(new HashMap<>());
    }

    public static State create() {
        return new EmptyState();
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
