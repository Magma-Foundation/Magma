package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void empty(){
        assertCompile("",
            "struct ___index___{};" +
            "struct ___index___ __index__(){" +
            "struct ___index___ this={};" +
            "return this;}");
    }

    private void assertCompile(String input, String output) {
        var compiler = new Compiler("index", input);
        var actual = compiler.compile();
        assertEquals(output, actual);
    }

    @Test
    void value(){
        assertCompile("const x : I16 = 20;",
            "struct ___index___{};" +
            "struct ___index___ __index__(){" +
            "struct ___index___ this={};" +
            "int x=20;" +
            "return this;}");
    }

    @Test
    void type(){
        assertCompile("const x : U16 = 20;",
            "struct ___index___{};" +
            "struct ___index___ __index__(){" +
            "struct ___index___ this={};" +
            "unsigned int x=20;" +
            "return this;}");
    }
}