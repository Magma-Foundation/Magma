package com.meti.node;

import java.util.ArrayList;
import java.util.List;

public class ObjectListAttribute implements Attribute {
    private final List<?> nodes;

    public ObjectListAttribute(List<?> values) {
        this.nodes = new ArrayList<>(values);
    }
}
