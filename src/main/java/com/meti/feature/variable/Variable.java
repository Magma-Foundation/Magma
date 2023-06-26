package com.meti.feature.variable;

import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record Variable(NativeString value1) implements Node {
    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    @Override
    public Option<NativeString> valueAsString() {
        return Some.apply(value1);
    }

    enum Key {
        Id
    }
}