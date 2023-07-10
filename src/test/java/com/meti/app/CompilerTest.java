package com.meti.app;

import com.meti.core.Results;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CompilerTest {
    @Test
    void empty() throws CompileException {
        assertTrue(Results.unwrap(new Compiler(JavaString.empty()).compile()).isEmpty());
    }

    @Test
    void testPackage() throws CompileException {
        var unwrap = Results.unwrap(new Compiler(new JavaString("package test;")).compile());
        assertTrue(unwrap.isEmpty());
    }
}