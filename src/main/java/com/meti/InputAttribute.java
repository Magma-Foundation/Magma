package com.meti;

import com.meti.compile.Input;

public record InputAttribute(Input value) implements Attribute {
    @Override
    public Input asInput() {
        return value;
    }
}
