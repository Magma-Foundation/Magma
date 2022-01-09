package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class BlockTest {
    @Test
    void empty_block() {
        assertCompile("{}", "{}");
    }

    @Test
    void one_line() {
        assertCompile("{return 420}", "{return 420;}");
    }

    @Test
    void two_lines() {
        assertCompile("{return 69;return 420;}", "{return 69;return 420;}");
    }
}
