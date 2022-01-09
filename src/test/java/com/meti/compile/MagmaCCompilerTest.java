package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

class MagmaCCompilerTest {
    @Test
    void compile() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    @Test
    void multiple() {
        assertCompile("return 420;return 69;", "return 420;return 69;");
    }
}