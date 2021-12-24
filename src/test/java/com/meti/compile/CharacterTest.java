package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class CharacterTest {
    @Test
    void implicit() {
        assertCompile("const x='x';", "char x='x';");
    }

    @Test
    void test() {
        assertCompile("const x : I8 = 'x';", "char x='x';");
    }
}
