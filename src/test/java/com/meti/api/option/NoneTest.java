package com.meti.api.option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoneTest {

    @Test
    void orElse() {
        assertEquals(4, new None<>().orElse(4));
    }
}