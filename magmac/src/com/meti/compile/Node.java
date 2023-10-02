package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;

public interface Node {
    default Option<Node> with(JavaString name, Attribute attribute) {
        return None.apply();
    }

    default Option<Attribute> apply(JavaString name) {
        return None.apply();
    }


    boolean is(String name);
}
