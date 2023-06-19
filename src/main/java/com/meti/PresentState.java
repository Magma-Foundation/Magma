package com.meti;

import java.util.Map;
import java.util.function.Function;

final class PresentState extends State {
    final String value;

    PresentState(String value, Map<String, String> declarations) {
        super(declarations);
        this.value = value;
    }

    @Override
    public PresentState withValue(String value) {
        return new PresentState(value, declarations);
    }

    PresentState mapValue(Function<String, String> mapper) {
        return new PresentState(mapper.apply(this.value), this.declarations);
    }

    PresentState define(String name, String value) {
        this.declarations.put(name, value);
        return this;
    }

    @Override
    public Option<String> findValue() {
        return new Some<>(value);
    }
}
