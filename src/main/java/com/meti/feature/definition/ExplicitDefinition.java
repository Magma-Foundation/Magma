package com.meti.feature.definition;

import com.meti.feature.attribute.Attribute;
import com.meti.feature.attribute.StringAttribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public final class ExplicitDefinition extends ImplicitDefinition {
    private final NativeString type;

    public ExplicitDefinition(NativeString name, NativeString type, NativeString value) {
        super(name, value);
        this.type = type;
    }

    @Override
    public Option<Attribute> apply(NativeString key) {
        return super.apply(key).orElseGet(() -> {
            if (key.equalsTo(NativeString.from("type"))) {
                return Some.apply(new StringAttribute(type));
            } else {
                return None.apply();
            }
        });
    }

    @Override
    public ExplicitDefinition withValue(NativeString value) {
        return new ExplicitDefinition(name, type, value);
    }
}
