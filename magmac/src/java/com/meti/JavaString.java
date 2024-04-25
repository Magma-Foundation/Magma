package com.meti;

public record JavaString(String value) {
    public static final JavaString EMPTY = new JavaString("");

    public JavaString concat(JavaString other) {
        return new JavaString(value + other.value);
    }
}
