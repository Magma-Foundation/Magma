package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void empty(){
        var output = new Compiler("index", "").compile();
        assertEquals("struct ___index___{};struct ___index___ __index__(){struct ___index___ this={};return this;}", output);
    }

    @Test
    void value(){
        var output = new Compiler("index", "const x : I16 = 20;").compile();
        assertEquals("struct ___index___{};" +
                     "struct ___index___ __index__(){" +
                     "struct ___index___ this={};" +
                     "int x=20;" +
                     "return this;}", output);
    }
}