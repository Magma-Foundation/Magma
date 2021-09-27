package com.meti;

import org.junit.jupiter.api.Test;

public class BlockTest extends CompilerTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }

    @Test
    void one_line() {
        assertCompile("{const x : I16 = 420;}", "{int x=420;}");
    }

    @Test
    void two_lines() {
        assertCompile("{const x : I16 = 420;const y : U16 = 69;}", "{int x=420;unsigned int y=69;}");
    }
}
