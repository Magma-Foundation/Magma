package com.meti.attribute;

import com.meti.Input;

public record InputAttribute(Input input) implements Attribute {
    @Override
    public Input asInput() {
        return input;
    }
}
