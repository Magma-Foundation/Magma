package com.meti.app.compile.node.attribute;

import com.meti.app.compile.node.Input;

public record InputAttribute(Input input) implements Attribute {
    @Override
    public Input asInput() {
        return input;
    }
}
