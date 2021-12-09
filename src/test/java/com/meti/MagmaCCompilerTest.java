package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaCCompilerTest {
    @Test
    void name() {
        var output = new MagmaCCompiler("def test() : I16 => {return 0;}")
                .compile();
        assertEquals("int test(){return 0;}", output);
    }
}