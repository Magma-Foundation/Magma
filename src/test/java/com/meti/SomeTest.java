package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SomeTest {
    @Test
    void is_present(){
        assertTrue(new Some<>(null).isPresent());
    }
}