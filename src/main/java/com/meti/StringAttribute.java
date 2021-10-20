package com.meti;

public record StringAttribute(String value) implements Attribute {
    @Override
    public String asString() throws AttributeException {
        return value;
    }
}
