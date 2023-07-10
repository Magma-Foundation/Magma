package com.meti.collect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndexTest {
    @Test
    void nextExclusiveSequence() {
        var first = new Index(0, 3).nextExclusive().unwrap();
        assertEquals(1, first.value());
        var second = first.nextExclusive().unwrap();
        assertEquals(2, second.value());
        assertTrue(second.nextExclusive().isEmpty());
    }

    @Test
    void nextExclusiveInvalid() {
        var index = new Index(0, 1);
        assertTrue(index.nextExclusive().isEmpty());
    }

    @Test
    void nextExclusiveValid() {
        var index = new Index(0, 2);
        assertTrue(index.nextExclusive().isPresent());
    }
}