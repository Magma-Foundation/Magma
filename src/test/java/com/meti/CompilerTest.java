package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    private static void assertCompile(String input, String expected) {
        var output = new Compiler(input).compile();
        assertEquals(expected, output);
    }

    private static void assertCompile(Node input, Node output) {
        assertCompile(input.render(), output.render());
    }

    @Test
    void importAnother() {
        var value = new Import("foo");
        assertCompile(value, value);
    }

    @Test
    void importNamespace() {
        assertCompile(new Import("foo.Bar"), new Import("Bar from foo"));
    }

    @Test
    void importMultiple() {
        var first = new Import("first");
        var second = new Import("second");
        assertCompile(first.render() + "\n" + second.render(),
                first.render() + "\n" + second.render());
    }
}