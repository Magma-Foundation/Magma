package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static String run(String s) throws CompileException {
        var first = s.indexOf("package");
        if (first == -1) return "";

        var second = s.indexOf("package", first + "package ".length());
        if (second == -1) return "";

        throw new CompileException("Duplicate package statement.");
    }

    @Test
    void testEmpty() {
        assertEquals("", runImpl(""));
    }

    private static String runImpl(String s) {
        try {
            return run(s);
        } catch (CompileException e) {
            return fail(e);
        }
    }

    @Test
    void testPackages() {
        assertEquals("", runImpl("package com.meti;"));
    }

    @Test
    void testMultiplePackages() {
        assertThrows(CompileException.class, () -> run("package first;package second"));
    }
}
