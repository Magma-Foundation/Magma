package com.meti.option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SomeTest {

    @Test
    void orElse() {
        assertEquals(4, new Some<>(4).orElse(0));
    }
}