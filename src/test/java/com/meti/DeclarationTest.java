package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationTest extends FeatureTest {
    @Test
    void test() {
        assertCompiles("const x : I16 = 0", "");
    }
}
