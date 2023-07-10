package com.meti.app;

import com.meti.core.Results;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    private static void assertEmpty(JavaString input) {
        try {
            assertTrue(compileImpl(input).isEmpty());
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static JavaString compileImpl(JavaString input) throws CompileException {
        try {
            return Results.unwrap(new Compiler(input).compile());
        } catch (CompileException e) {
            return fail(e);
        }
    }

    private static void assertCompile(String value) {
        try {
            var importTest = compileImpl(new JavaString(value)).unwrap();
            assertEquals(value, importTest);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testImport(String name) {
        assertCompile("import " + name);
    }

    @Test
    void empty() {
        assertEmpty(JavaString.empty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testPackage(String name) {
        assertEmpty(new JavaString("package %s;".formatted(name)));
    }
}