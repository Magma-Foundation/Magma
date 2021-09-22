package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SomeTest {
    @Test
    void is_present() {
        assertTrue(new Some<>(null).isPresent());
    }

    @Test
    void map() {
        assertEquals(4, new Some<>("test")
                .map(String::length)
                .orElse(-1));
    }
}