package com.meti.compile;

public record InputAttribute(Input value) implements Attribute {
    @Override
    public Input asInput() {
        return value;
    }
}
