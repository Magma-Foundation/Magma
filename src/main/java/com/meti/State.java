package com.meti;

import java.util.Map;

public abstract class State {
    final Map<String, String> declarations;

    public State(Map<String, String> declarations) {
        this.declarations = declarations;
    }

    public abstract Option<String> findValue();

    public abstract PresentState withValue(String value);
}
