package com.meti.node;

import com.meti.util.Option;

public interface AttributeFactory<T> {
    Attribute toAttribute(T value);

    Option<T> fromAttribute(Attribute attribute);
}
