package com.meti.compile.node.attribute;

import com.meti.compile.node.Input;

public record InputAttribute(Input input) implements Attribute {
    @Override
    public Input asInput() {
        return input;
    }
}
