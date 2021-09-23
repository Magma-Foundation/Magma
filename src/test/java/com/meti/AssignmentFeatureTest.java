package com.meti;

import org.junit.jupiter.api.Test;

public class AssignmentFeatureTest extends FeatureTest {
    @Test
    void assignment() {
        assertAssignment("x", "69");
    }

    private void assertAssignment(String name, String value) {
        var assignment = new Assignment(name, value);
        assertCompile("let " + name + " : I16 = 420;" + assignment.renderMagma(),
                "int " + name + "=420;" + assignment.renderNative());
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
