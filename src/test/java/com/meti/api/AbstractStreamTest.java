package com.meti.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractStreamTest {
    @Test
    void flatMap() throws StreamException {
        var expected = 420;
        var actual = new ArrayStream<>(new ArrayStream<>(expected))
                .flatMap(value -> value)
                .head();
        assertEquals(expected, actual);
    }
}