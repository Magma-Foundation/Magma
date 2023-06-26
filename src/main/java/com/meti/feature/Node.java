package com.meti.feature;

import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;

public interface Node {
    default Option<Attribute> apply(NativeString name) {
        return None.apply();
    }

    default Option<Node> withAttribute(NativeString name, Attribute attribute) {
        return None.apply();
    }

    default Option<Integer> valueAsInt() {
        return None.apply();
    }

    default Option<Node> valueAsNode() {
        return None.apply();
    }

    boolean is(Object key);

    default Option<NativeString> nameAsString() {
        return None.apply();
    }

    default Option<NativeString> valueAsString() {
        return None.apply();
    }

    default Option<NativeString> typeAsString() {
        return None.apply();
    }
}
