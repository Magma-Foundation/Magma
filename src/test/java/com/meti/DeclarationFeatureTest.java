package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationFeatureTest extends CompilerTest {
    @Test
    void declaration() {
        assertCompile("const x : I16 = 420", "int x=420;");
    }

    @Test
    void name() {
        assertCompile("const test : I16 = 420", "int test=420;");
    }

    @Test
    void type() {
        assertCompile("const test : U16 = 420", "unsigned int test=420;");
    }

    @Test
    void value() {
        assertCompile("const value : I16 = 69", "int value=69;");
    }
}
