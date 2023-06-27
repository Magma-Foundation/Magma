package com.meti.feature.attribute;

import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;

public interface Attribute {
    default Option<SafeList<? extends Node>> asListOfNodes() {
        return None.apply();
    }

    default Option<Node> asNode() {
        return None.apply();
    }

    default Option<NativeString> asString() {
        return None.apply();
    }

    default Option<Integer> asInteger() {
        return None.apply();
    }
}
