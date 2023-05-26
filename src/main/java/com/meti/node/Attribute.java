package com.meti.node;

public interface Attribute {
    public interface Converter<T> {
        Attribute fromValue(T value);

        T fromAttribute(Attribute value);

        T apply(T value);
    }
}
