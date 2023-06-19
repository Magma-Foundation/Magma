package com.meti;

import java.util.Map;

public abstract class State {
    final Map<NativeString, NativeString> declarations;

    public State(Map<NativeString, NativeString> declarations) {
        this.declarations = declarations;
    }

    PresentState define(NativeString name, NativeString value) {
        this.declarations.put(name, value);
        return new PresentState(name, this.declarations);
    }

    public abstract Option<NativeString> findValue();

    public abstract PresentState withValue(NativeString value);
}
