package com.meti.app;

import org.junit.jupiter.api.Test;

import static com.meti.core.Results.unwrap;
import static com.meti.java.JavaString.Empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void empty() throws CompileException {
        var actual = unwrap(new Compiler(Empty).compile());
        assertEquals("", actual.unwrap());
    }
}