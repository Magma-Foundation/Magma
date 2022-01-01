package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void name() {
        var output = new Compiler("def supplier() : I16 => {return 0;}")
                .compile();
        assertEquals("int supplier(){return 0;}", output);
    }
}