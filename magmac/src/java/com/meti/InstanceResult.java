package com.meti;

import java.util.Collections;
import java.util.List;

record InstanceResult(List<String> value) implements Result {
    InstanceResult(String value) {
        this(Collections.singletonList(value));
    }

    InstanceResult(List<String> value) {
        this.value = value;
    }

    @Override
    public List<String> staticValue() {
        return Collections.emptyList();
    }

    @Override
    public List<String> instanceValue() {
        return value;
    }
}
