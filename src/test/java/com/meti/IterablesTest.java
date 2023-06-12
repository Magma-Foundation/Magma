package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IterablesTest {

    @Test
    void empty() {
        assertTrue(Iterables.empty().head().isEmpty());
    }

    @Test
    void of() {
        var expected = "test";
        var actual = Iterables.of(expected).head().unwrapOrPanic();
        assertEquals(expected, actual);
    }
}