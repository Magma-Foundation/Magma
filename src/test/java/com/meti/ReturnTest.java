package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTests.assertCompile;

class ReturnTest {
    private static void assertInteger(final String value) {
        var rendered = Compiler.renderReturns(value);
        assertCompile(rendered, rendered);
    }

    @Test
    void returns() {
        assertInteger("420");
    }

    @Test
    void another() {
        assertInteger("69");
    }
}