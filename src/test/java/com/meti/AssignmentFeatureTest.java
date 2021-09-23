package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssignmentFeatureTest extends FeatureTest {
    @Test
    void immutable() {
        Assertions.assertThrows(ApplicationException.class, () -> {
            var first = new Declaration(Declaration.Flag.CONST, "test", PrimitiveType.I16, "100").renderMagma();
            var second = new Assignment("test", "400").renderMagma();
            new Compiler(first + second).compile();
        });
    }

    @Test
    void assignment() {
        assertAssignment("x", "69");
    }

    private void assertAssignment(String name, String value) {
        var declaration = new Declaration(Declaration.Flag.LET, name, PrimitiveType.I16, value);
        var assignment = new Assignment(name, value);
        assertCompile(declaration.renderMagma() + assignment.renderMagma(),
                declaration.renderNative() + assignment.renderNative());
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
