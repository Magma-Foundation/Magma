package com.meti.feature.definition;

import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public final class ExplicitDefinition extends ImplicitDefinition {
    private final NativeString type;

    public ExplicitDefinition(NativeString name, NativeString type, NativeString value) {
        super(name, value);
        this.type = type;
    }

    @Override
    public Option<NativeString> typeAsString() {
        return Some.apply(type);
    }

    @Override
    public ExplicitDefinition withValue(NativeString value) {
        return new ExplicitDefinition(name, type, value);
    }
}
