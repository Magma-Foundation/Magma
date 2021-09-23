package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationFeatureTest extends FeatureTest {
    @Test
    void no_type() {
        assertCompile("const x = 420;", "int x=420;");
    }

    @Test
    void declaration() {
        assertDeclaration(new Declaration(Declaration.Flag.CONST, "x", PrimitiveType.I16, "420"));
    }

    private void assertDeclaration(Node node) {
        var input = node.renderMagma();
        var expected = node.renderNative();
        assertCompile(input, expected);
    }

    @Test
    void declaration_name() {
        assertDeclaration(new Declaration(Declaration.Flag.CONST, "test", PrimitiveType.I16, "420"));
    }

    @Test
    void declaration_type() {
        assertDeclaration(new Declaration(Declaration.Flag.CONST, "x", PrimitiveType.U16, "420"));
    }

    @Test
    void declaration_value() {
        assertDeclaration(new Declaration(Declaration.Flag.CONST, "x", PrimitiveType.U16, "69"));
    }

    @Test
    void flag() {
        assertDeclaration(new Declaration(Declaration.Flag.LET, "x", PrimitiveType.I16, "420"));
    }
}
