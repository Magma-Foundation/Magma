package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class ReferenceTest {
    @Test
    void nominal() {
        assertSourceCompile("const x : &I16 = 0", "int *x=0;");
    }
}
