package com.meti;

public interface Attribute {
    public interface Converter<T> {
        Attribute fromValue(T value);

        T fromAttribute(Attribute value);

        T apply(T value);
    }
}
