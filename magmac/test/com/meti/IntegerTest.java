package com.meti;

import org.junit.jupiter.api.Test;

public class IntegerTest extends FeatureTest {
    @Test
    void simple() {
        assertCompile("0", "0");
    }
}
