package com.meti;

import java.util.Map;

public abstract class State {
    final Map<NativeString, NativeString> declarations;

    public State(Map<NativeString, NativeString> declarations) {
        this.declarations = declarations;
    }

    State define(Definition definition) {
        this.declarations.put(definition.name(), definition.value());
        return this;
    }

    public abstract Option<NativeString> findValue();

    public abstract PresentState withValue(NativeString value);
}
