package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertDoesNotCompile;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class BlockTest {
    @Test
    void defined() {
        assertSourceCompile("const x=420;{x}", "int x=420;{x}");
    }

    @Test
    void empty_block() {
        assertSourceCompile("{}", "{}");
    }

    @Test
    void inner() {
        assertSourceCompile("{{}{}}", "{{}{}}");
    }

    @Test
    void one_line() {
        assertSourceCompile("{return 420}", "{return 420;}");
    }

    @Test
    void two_lines() {
        assertSourceCompile("{return 69;return 420;}", "{return 69;return 420;}");
    }

    @Test
    void undefined() {
        assertDoesNotCompile("{const x = 420}x");
    }
}
