package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

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
