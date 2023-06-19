package com.meti;

import java.util.Map;

final class PresentState extends State {
    final String value;

    PresentState(String value, Map<String, String> declarations) {
        super(declarations);
        this.value = value;
    }

    @Override
    public Option findValue() {
        return new Some(value);
    }
}
