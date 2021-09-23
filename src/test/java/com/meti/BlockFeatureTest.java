package com.meti;

import org.junit.jupiter.api.Test;

public class BlockFeatureTest extends FeatureTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }

    @Test
    void one() {
        assertCompile("{const x = 420;}", "{int x=420;}");
    }
}
