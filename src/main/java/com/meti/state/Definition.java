package com.meti.state;

import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record Definition(NativeString name, NativeString type, NativeString value) implements Node {
    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    @Override
    public Option<NativeString> typeAsString() {
        return Some.apply(type);
    }

    @Override
    public Option<NativeString> nameAsString() {
        return Some.apply(name);
    }

    @Override
    public Option<NativeString> valueAsString() {
        return Some.apply(value);
    }

    public Definition withValue(NativeString value) {
        return new Definition(name, type, value);
    }

    enum Key {
        Id
    }
}
