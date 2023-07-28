package com.meti.app;

import org.junit.jupiter.api.Test;

public class BlockTest extends CompiledTest {
    @Test
    void empty() {
        assertCompile("{}", "{\n}\n");
    }

    @Test
    void within() {
        assertCompile("{{}}", "{\n\t{\n\t}\n}\n");
    }
}
