package com.meti;

import org.junit.jupiter.api.Test;

public class BlockFeatureTest extends FeatureTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }
}
