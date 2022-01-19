package com.meti.app.compile.node.attribute;

import com.meti.app.compile.text.Input;

public record InputAttribute(Input value) implements Attribute {
    @Override
    public Input asInput() {
        return value;
    }
}
