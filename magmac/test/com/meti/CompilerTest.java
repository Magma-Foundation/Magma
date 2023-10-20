package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    private static void assertCompile(String input, String output) {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }

    @Test
    void records() {
        assertCompile("record Test(){}", "class def Test() => {}");
    }

    @Test
    void imports() {
        assertCompile("import parent.Child", "import { Child } from parent;");
    }

    @Test
    void removePackage() {
        assertCompile("package test;class Test {}", "class def Test() => {}");
    }
}