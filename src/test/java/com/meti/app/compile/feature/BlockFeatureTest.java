package com.meti.app.compile.feature;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

public class BlockFeatureTest {
    @Test
    void empty() {
        assertCompile("{}", "{}");
    }

    @Test
    void one() {
        assertCompile("{return 420;}", "{return 420;}");
    }

    @Test
    void two() {
        assertCompile("{return 420;return 69;}", "{return 420;return 69;}");
    }
}
