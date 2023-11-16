package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    @Test
    void multiple(){
        var output = new Compiler("import a.B;import c.D").compile();
        assertEquals("import { B } from a;import { D } from c;", output);
    }
}