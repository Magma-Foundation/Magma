package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void importAnother() {
        var content = new Import("foo").render();
        assertCompile(content, content);
    }

    private static void assertCompile(String input, String expected) {
        var output = new Compiler(input).compile();
        assertEquals(expected, output);
    }

    @Test
    void importNamespace() {
        assertCompile(new Import("foo.Bar").render(), new Import("Bar from foo").render());
    }
}