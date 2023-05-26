package com.meti.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ObjectListAttribute implements Attribute {
    private final List<?> nodes;

    public ObjectListAttribute() {
        this(Collections.emptyList());
    }

    public ObjectListAttribute(List<?> values) {
        this.nodes = new ArrayList<>(values);
    }

    @Override
    public Optional<List<?>> asListOfObjects() {
        return Optional.of(nodes);
    }
}
