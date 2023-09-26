package com.meti.compile.attribute;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Attribute;

public class StringAttribute implements Attribute {
    private final JavaString value;

    public StringAttribute(JavaString value) {
        this.value = value;
    }

    @Override
    public Option<JavaString> asString() {
        return Some.apply(value);
    }
}
