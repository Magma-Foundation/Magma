package com.meti.feature.assign;

import com.meti.feature.attribute.Attribute;
import com.meti.feature.Node;
import com.meti.feature.attribute.StringAttribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record Assignment(NativeString name, NativeString value) implements Node {
    @Override
    public boolean is(Object key) {
        return Key.Id.equals(key);
    }

    @Override
    public Option<Attribute> apply(NativeString key) {
        if (key.equalsTo(NativeString.from("value"))) {
            return Some.apply(new StringAttribute(this.value));
        } else if (key.equalsTo(NativeString.from("name"))) {
            return Some.apply(new StringAttribute(this.name));
        } else {
            return None.apply();
        }
    }

    enum Key {
        Id
    }
}