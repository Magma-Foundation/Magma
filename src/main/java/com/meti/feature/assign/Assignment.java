package com.meti.feature.assign;

import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record Assignment(NativeString name, NativeString value) implements Node {
    @Override
    public boolean is(Object key) {
        return Key.Id.equals(key);
    }

    @Override
    public Option<NativeString> valueAsString() {
        return Some.apply(value);
    }

    @Override
    public Option<NativeString> nameAsString() {
        return Some.apply(name);
    }

    enum Key {
        Id
    }
}