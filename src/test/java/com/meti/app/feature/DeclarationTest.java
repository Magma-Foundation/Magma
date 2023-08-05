package com.meti.app.feature;

import org.junit.jupiter.api.Test;

class DeclarationTest extends CompiledTest {
    @Test
    void declarationKeyword() {
        assertCompile("private int x", "x : I16");
    }

    @Test
    void declaration() {
        assertCompile("int x", "x : I16");
    }

    @Test
    void declarationName() {
        assertCompile("int test", "test : I16");
    }
}