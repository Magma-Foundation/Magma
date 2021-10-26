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
        assertCompile("{return first;return second;}", "{return first;return second;}");
    }
}
