package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final String PACKAGE_KEYWORD = "package";

    private static String run(String input) throws CompileException {
        var first = input.indexOf(PACKAGE_KEYWORD);
        if (first == -1) return "";

        var second = input.indexOf(PACKAGE_KEYWORD, first + (PACKAGE_KEYWORD + " ").length());
        if (second == -1) return "";

        throw new CompileException("Duplicate package statement.");
    }

    private static String runImpl(String s) {
        try {
            return run(s);
        } catch (CompileException e) {
            return fail(e);
        }
    }

    private static String renderPackage(String namespace) {
        return PACKAGE_KEYWORD + " " + namespace + ";";
    }

    private static void assertCompile(String input, String output) {
        assertEquals(output, runImpl(input));
    }

    @Test
    void testEmpty() {
        assertCompile("", "");
    }

    @Test
    void testPackages() {
        assertCompile(renderPackage("com.meti"), "");
    }

    @Test
    void testMultiplePackages() {
        assertThrows(CompileException.class, () -> run(renderPackage("first") + renderPackage("second")));
    }
}
