package com.meti;

import java.util.Collections;
import java.util.List;

record StaticResult(List<String> value) implements Result {
    StaticResult(String value) {
        this(Collections.singletonList(value));
    }

    @Override
    public List<String> staticValue() {
        return value;
    }

    @Override
    public List<String> instanceValue() {
        return Collections.emptyList();
    }
}
