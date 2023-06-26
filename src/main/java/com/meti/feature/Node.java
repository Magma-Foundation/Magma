package com.meti.feature;

import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;

public interface Node {
    default Option<SafeList<? extends Node>> lines() {
        return None.apply();
    }

    default Node withLines(SafeList<? extends Node> lines) {
        return this;
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
