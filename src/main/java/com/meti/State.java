package com.meti;

import java.util.HashMap;
import java.util.Map;

public class State {
    final Map<String, String> declarations;

    public State(Map<String, String> declarations) {
        this.declarations = declarations;
    }

    public State() {
        this(new HashMap<>());
    }

    public Option<String> findValue() {
        return new None<>();
    }

    public PresentState withValue(String value) {
        return new PresentState(value, declarations);
    }
}
