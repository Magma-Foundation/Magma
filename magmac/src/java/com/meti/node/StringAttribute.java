package com.meti.node;

import com.meti.collect.JavaString;
import com.meti.option.Option;
import com.meti.option.Some;

public class StringAttribute implements Attribute {
    private final JavaString value;

    public StringAttribute(JavaString value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Option<JavaString> asString() {
        return new Some<>(value);
    }
}
