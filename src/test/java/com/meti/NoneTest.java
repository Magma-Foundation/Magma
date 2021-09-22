package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class NoneTest {
    @Test
    void is_present() {
        assertFalse(new None<>().isPresent());
    }

    @Test
    void map() {
        assertFalse(new None<>().map(null).isPresent());
    }
}