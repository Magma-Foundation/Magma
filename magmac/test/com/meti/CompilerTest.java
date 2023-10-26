package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    private static void assertCompile(String input, String output) {
        try {
            var actual = new Compiler(input).compile();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void records() {
        assertCompile("record Test(){}", "class def Test() => {}");
    }

    @Test
    void multipleImports() {
        assertCompile("import parent.Child;import parent.Sibling;",
                "import { Child } from parent;import { Sibling } from parent;");
    }
}