package com.meti.app.feature;

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
