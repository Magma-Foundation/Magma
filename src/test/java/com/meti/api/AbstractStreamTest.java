package com.meti.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractStreamTest {
    @Test
    void flat_map_once() throws StreamException {
        var expected = 420;
        var actual = new ArrayStream<>(new ArrayStream<>(expected))
                .flatMap(value -> value)
                .head();
        assertEquals(expected, actual);
    }

    @Test
    void flat_map_twice() throws StreamException {
        var actual = new ArrayStream<>(new ArrayStream<>(1, 2))
                .flatMap(value -> value)
                .foldRight(0, Integer::sum);
        assertEquals(3, actual);
    }

    @Test
    void flat_map_double() throws StreamException {
        var actual = new ArrayStream<>(new ArrayStream<>(1), new ArrayStream<>(2))
                .flatMap(value -> value)
                .foldRight(0, Integer::sum);
        assertEquals(3, actual);
    }
}