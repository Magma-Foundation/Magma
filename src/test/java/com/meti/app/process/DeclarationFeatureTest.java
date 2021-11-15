package com.meti.app.process;

import org.junit.jupiter.api.Test;

import static com.meti.app.CompiledTest.assertCompile;

public class DeclarationFeatureTest {
    @Test
    void name() {
        assertCompile("const y : I16 = 420", "int y=420;");
    }

    @Test
    void test() {
        assertCompile("const x : I16 = 420", "int x=420;");
    }

    @Test
    void type() {
        assertCompile("const x : U16 = 420", "unsigned int x=420;");
    }

    @Test
    void value() {
        assertCompile("const x : I16 = 69", "int x=69;");
    }
}
