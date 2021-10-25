package com.meti.feature;

import com.meti.Attribute;

public interface Node {
    boolean is(Import.Type type);

    Attribute apply(Attribute.Type type);
}
