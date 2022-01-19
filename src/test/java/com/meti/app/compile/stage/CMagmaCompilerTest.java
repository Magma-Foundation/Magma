package com.meti.app.compile.stage;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

class CMagmaCompilerTest {
    @Test
    void compile() {
        assertSourceCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    @Test
    void multiple() {
        assertSourceCompile("return 420;return 69;", "return 420;return 69;");
    }
}