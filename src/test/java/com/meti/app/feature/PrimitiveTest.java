package com.meti.app.feature;

import org.junit.jupiter.api.Test;

public class PrimitiveTest extends CompiledTest {
    @Test
    void bool() {
        assertCompile("boolean", "Bool");
    }
}
