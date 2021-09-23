package com.meti;

import org.junit.jupiter.api.Test;

public class GroupFeatureTest extends FeatureTest {
    @Test
    void two() {
        assertCompile("const x : I16 = 420; const y : U16 = 69;", "int x=420;unsigned int y=69;");
    }
}
