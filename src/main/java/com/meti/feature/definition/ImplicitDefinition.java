package com.meti.feature.definition;

import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class ImplicitDefinition implements Node {
    protected final NativeString name;
    protected final NativeString value;


    public ImplicitDefinition(NativeString name, NativeString value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    @Override
    public Option<NativeString> nameAsString() {
        return Some.apply(name);
    }

    @Override
    public Option<NativeString> valueAsString() {
        return Some.apply(value);
    }

    public ImplicitDefinition withValue(NativeString value) {
        return new ImplicitDefinition(name, value);
    }

    enum Key {
        Id
    }
}
