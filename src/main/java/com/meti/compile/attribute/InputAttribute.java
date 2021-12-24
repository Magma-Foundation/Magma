package com.meti.compile.attribute;

import com.meti.compile.Input;

public record InputAttribute(Input input) implements Attribute {
    @Override
    public Input asInput() {
        return input;
    }
}
