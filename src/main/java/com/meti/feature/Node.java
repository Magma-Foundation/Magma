package com.meti.feature;

import com.meti.feature.attribute.Attribute;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;

public interface Node {
    default Option<Attribute> apply(NativeString key) {
        return None.apply();
    }

    default Option<Node> withAttribute(NativeString name, Attribute attribute) {
        return None.apply();
    }

    boolean is(Object key);
}
