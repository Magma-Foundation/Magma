package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        var actual = NativeString.toNative(NativeString.fromNative("test").slice(1, 3).unwrapOrPanic());
        assertEquals("es", actual);
    }

    @Test
    void concat() {
        var actual = NativeString.toNative(NativeString.fromNative("test").concat(NativeString.fromNative("ing")));
        assertEquals("testing", actual);
    }

    @Test
    void endsWithValid() {
        assertTrue(NativeString.fromNative("test").endsWith(NativeString.fromNative("est")));
    }

    @Test
    void endsWithInvalid() {
        assertFalse(NativeString.fromNative("test").endsWith(NativeString.fromNative("a")));
    }
}