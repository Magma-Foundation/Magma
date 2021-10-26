package com.meti.feature;

import com.meti.Input;
import com.meti.attribute.Attribute;

public record InputAttribute(Input input) implements Attribute {
    @Override
    public Input asInput() {
        return input;
    }
}
