package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    private static void assertCompile(String input, String expected) {
        try {
            var actual = Compiler.compile(input);
            assertEquals(expected, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void importStatic() {
        assertCompile("import static parent.Child", "import { Child } from parent;");
    }

    @Test
    void importStaticAll() {
        assertCompile("import static parent.*", "import * from parent;");
    }

    @Test
    void importName() {
        assertCompile("import org.junit.jupiter.api.Test;", "import { Test } from org.junit.jupiter.api;");
    }

    @Test
    void importParent() {
        assertCompile("import parent.Child;", "import { Child } from parent;");
    }

    @Test
    void multipleImports() {
        assertCompile("import first.Second;import third.Fourth",
                "import { Second } from first;\n" +
                "import { Fourth } from third;");
    }

    @Test
    void multipleWithWhitespace() {
        assertCompile("import first.Second;\n\nimport third.Fourth",
                "import { Second } from first;\n" +
                "import { Fourth } from third;");
    }
}