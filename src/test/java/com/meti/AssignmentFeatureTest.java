package com.meti;

import org.junit.jupiter.api.Test;

public class AssignmentFeatureTest extends FeatureTest {
    @Test
    void assignment() {
        assertAssignment("x");
    }

    private void assertAssignment(final String name) {
        assertCompile("let " + name + " : I16 = 420;" + name + " = 69;", "int " + name + "=420;" + name + "=69;");
    }

    @Test
    void name() {
        assertAssignment("test");
    }
}
