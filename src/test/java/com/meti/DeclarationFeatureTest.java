package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationFeatureTest extends FeatureTest {
    @Test
    void declaration() {
        assertDeclaration(new Declaration("x", Declaration.Flag.CONST, PrimitiveType.I16, "420"));
    }

    private void assertDeclaration(Node node) {
        var input = node.renderMagma();
        var expected = node.renderNative();
        assertCompile(input, expected);
    }

    @Test
    void declaration_name() {
        assertDeclaration(new Declaration("test", Declaration.Flag.CONST, PrimitiveType.I16, "420"));
    }

    @Test
    void declaration_type() {
        assertDeclaration(new Declaration("x", Declaration.Flag.CONST, PrimitiveType.U16, "420"));
    }

    @Test
    void declaration_value() {
        assertDeclaration(new Declaration("x", Declaration.Flag.CONST, PrimitiveType.U16, "69"));
    }

    @Test
    void flag() {
        assertDeclaration(new Declaration("x", Declaration.Flag.LET, PrimitiveType.I16, "420"));
    }
}
