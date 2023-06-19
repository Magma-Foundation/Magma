package com.meti;

import java.util.Map;

public class State {
    final Map<String, String> declarations;

    public State(Map<String, String> declarations) {
        this.declarations = declarations;
    }

    public Option findValue() {
        return new None();
    }
}
