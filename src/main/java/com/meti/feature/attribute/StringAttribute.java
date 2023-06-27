package com.meti.feature.attribute;

import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class StringAttribute implements Attribute {
    private final NativeString value;

    public StringAttribute(NativeString value) {
        this.value = value;
    }

    @Override
    public Option<NativeString> asString() {
        return Some.apply(value);
    }
}
