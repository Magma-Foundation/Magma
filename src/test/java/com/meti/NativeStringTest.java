package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NativeStringTest {
    @Test
    void toNative() {
        var expected = "0";
        var value = new NativeString(new char[]{expected.charAt(0)});
        var actual = NativeString.toNative(value);
        assertEquals(expected, actual);
    }

    @Test
    void indexOf() {
        var expected = "test";
        var library = NativeString.fromNative(expected);
        assertEquals(expected, NativeString.toNative(library));
    }

    @Test
    void slice() {
        var actual = NativeString.toNative(NativeString.fromNative("test").slice(1, 3));
        assertEquals("es", actual);
    }
}