package com.meti.feature;

import com.meti.Attribute;

public record StringAttribute(String value) implements Attribute {
    @Override
    public String asString() {
        return value;
    }
}
