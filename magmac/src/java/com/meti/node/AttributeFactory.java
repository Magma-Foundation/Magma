package com.meti.node;

import com.meti.api.option.Option;

public interface AttributeFactory<T> {
    boolean accepts(Attribute attribute);

    Attribute toAttribute(T value);

    Option<T> fromAttribute(Attribute attribute);
}
