package com.meti.app.compile.node.attribute;

import com.meti.app.compile.text.Output;

public record OutputAttribute(Output value) implements Attribute {
    @Override
    public Output asOutput() {
        return value;
    }
}
