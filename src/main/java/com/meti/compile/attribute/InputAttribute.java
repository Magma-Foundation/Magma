package com.meti.compile.attribute;

import com.meti.compile.node.Input;

public record InputAttribute(Input value) implements Attribute {
    @Override
    public Input asInput() {
        return value;
    }
}
