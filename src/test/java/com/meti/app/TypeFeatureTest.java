package com.meti.app;

import org.junit.jupiter.api.Test;

import static com.meti.app.CompiledTest.assertCompile;

public class TypeFeatureTest {
    @Test
    void valid() {
        assertCompile("type test = U16;const x : test = 420", "unsigned int x=420;");
    }
}