package com.meti;

import org.junit.jupiter.api.Test;

public class AssignmentFeatureTest extends FeatureTest {
    @Test
    void assignment() {
        assertAssignment("x", "69");
    }

    private void assertAssignment(final String name, final String value) {
        assertCompile("let " + name + " : I16 = 420;" + name + " = " + value + ";", "int " + name + "=420;" + name + "=" + value + ";");
    }

    @Test
    void name() {
        assertAssignment("test", "69");
    }

    @Test
    void value() {
        assertAssignment("x", "314");
    }
}
