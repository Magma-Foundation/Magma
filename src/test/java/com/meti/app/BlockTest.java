package com.meti.app;

import org.junit.jupiter.api.Test;

public class BlockTest extends CompiledTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }

    @Test
    void within() {
        assertCompile("{{}}", "{{}}");
    }
}
