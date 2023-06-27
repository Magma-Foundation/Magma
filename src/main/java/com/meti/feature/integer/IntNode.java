package com.meti.feature.integer;

import com.meti.feature.Node;
import com.meti.feature.attribute.Attribute;
import com.meti.feature.attribute.StringAttribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record IntNode(int value) implements Node {
    @Override
    public Option<Attribute> apply(NativeString key) {
        return key.equalsTo(NativeString.from("value"))
                ? Some.apply(new IntAttribute(value))
                : None.apply();
    }

    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    enum Key {
        Id
    }
}