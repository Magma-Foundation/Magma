package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

public class BlockTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }
}
