package com.meti;

import org.junit.jupiter.api.Test;

class CompilerTest extends FeatureTest {
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