package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoneTest {
    @Test
    void is_present(){
        assertFalse(new None().isPresent());
    }
}