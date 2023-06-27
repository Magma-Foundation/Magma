package com.meti.feature;

import com.meti.feature.attribute.Attribute;
import com.meti.feature.attribute.StringAttribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class Content implements Node {
    private final NativeString value;

    public Content(NativeString value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Content{" +
               "value=" + value +
               '}';
    }

    @Override
    public Option<Attribute> apply(NativeString key) {
        return key.equalsTo(NativeString.from("value"))
                ? Some.apply(new StringAttribute(key))
                : None.apply();
    }

    public Option<NativeString> valueAsString() {
        return Some.apply(value);
    }

    @Override
    public boolean is(Object key) {
        return key.equals(Key.Id);
    }

    public enum Key {
        Id
    }
}
