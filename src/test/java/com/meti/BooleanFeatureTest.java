package com.meti;

import org.junit.jupiter.api.Test;

public class BooleanFeatureTest extends FeatureTest {
    @Test
    void test_false() {
        assertCompile("false", "0");
    }

    @Test
    void test_true() {
        assertCompile("true", "1");
    }
}
