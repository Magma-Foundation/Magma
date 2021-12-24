package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class BlockTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }

    @Test
    void multiple() {
        assertCompile("{const x : I16 = 420;const y : U16 = 69;}",
                "{int x=420;unsigned int y=69;}");
    }

    @Test
    void single() {
        assertCompile("{const x : I16 = 420;}", "{int x=420;}");
    }
}
