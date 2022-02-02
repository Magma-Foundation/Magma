package com.meti.app.compile.node.attribute;

public abstract class AbstractAttribute implements Attribute {
    @Override
    public String toString() {
        return toJSON().toString();
    }
}
