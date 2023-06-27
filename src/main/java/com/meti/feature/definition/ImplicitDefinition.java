package com.meti.feature.definition;

import com.meti.feature.Node;
import com.meti.feature.attribute.Attribute;
import com.meti.feature.attribute.StringAttribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
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
    public Option<Attribute> apply(NativeString key) {
        if (key.equalsTo(NativeString.from("name"))) {
            return Some.apply(new StringAttribute(this.name));
        } else if (key.equalsTo(NativeString.from("value"))) {
            return Some.apply(new StringAttribute(this.value));
        } else {
            return None.apply();
        }
    }

    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    public Option<NativeString> nameAsString() {
        return Some.apply(name);
    }

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
