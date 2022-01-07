package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {

    @Test
    void compile() {
        var output = new Compiler("def test() : I16 => {return 0;}")
                .compile();
        assertEquals("int test(){return 0;}", output);
    }
}