package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MagmaCCompilerTest {
    @Test
    void compile() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    private void assertCompile(String input, String output) {
        try {
            var compiler = new MagmaCCompiler(input);
            var actual = compiler.compile();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void empty_block() {
        assertCompile("{}", "{}");
    }

    @Test
    void integer() {
        assertCompile("420", "420");
    }

    @Test
    void return_type() {
        assertCompile("def test() : U16 => {return 0;}", "unsigned int test(){return 0;}");
    }

    @Test
    void returns() {
        assertCompile("return 69", "return 69;");
    }
}