package com.meti;

import org.junit.jupiter.api.Test;

public class AssignmentFeatureTest extends FeatureTest {
    @Test
    void valid() {
        assertCompile("let x : I16 = 420;x = 69;", "int x=420;x=69;");
    }
}
