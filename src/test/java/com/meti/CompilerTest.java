package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompilerTest {

    @Test
    void test_import() {
        assertThrows(CompileException.class, () -> new Compiler(new EmptyClassPath(), "import Test").compile());
    }

    @Test
    void test_valid_import() throws CompileException {
        assertEquals("#include \"Test.h\"\n", new Compiler(new SingleClassPath("Test"), "import Test").compile());
    }
}