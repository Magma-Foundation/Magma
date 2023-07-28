package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.Option;
import com.meti.java.String_;

public interface Node {
    boolean is(String_ name);

    Option<Node> with(String_ key, Attribute attribute);

    Option<Attribute> apply(String_ key);
}
