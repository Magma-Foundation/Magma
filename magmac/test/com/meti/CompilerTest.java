package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CompilerTest extends FeatureTest {
    @Test
    void split() {
        var expected = new String[]{"class Test {int value = 0;}"};
        var actual = Compiler.split("class Test {int value = 0;}");
        assertArrayEquals(expected, actual);
    }

    @Test
    void discardPackage() {
        assertCompile("package test;", "");
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