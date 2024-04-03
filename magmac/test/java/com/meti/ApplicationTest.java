package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final String PACKAGE_KEYWORD = "package";
    public static final String IMPORT_KEYWORD = "import ";
    public static final String TEST_PARENT = "org.junit.jupiter.api";
    public static final String TEST_CHILD = "Test";

    private static String renderJavaImport(String parent, String child) {
        return IMPORT_KEYWORD + parent + "." + child + ";";
    }

    private static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD + "{ " + child + " } " + parent + ";";
    }

    private static String run(String input) throws CompileException {
        var lines = Arrays.stream(input.split(";")).toList();
        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(runForRootStatement(line));
        }
        return builder.toString();
    }

    private static String runForRootStatement(String input) throws CompileException {
        if (input.startsWith(IMPORT_KEYWORD)) {
            var separator = input.lastIndexOf('.');
            var parent = input.substring(IMPORT_KEYWORD.length(), separator);

            var name = input.substring(separator + 1).strip();
            return renderMagmaImport(parent, name);
        }

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

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testImportChildren(String child) {
        assertCompile(renderJavaImport(TEST_PARENT, child), renderMagmaImport(TEST_PARENT, child));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "Second"})
    void testImportParents(String parent) {
        assertCompile(renderJavaImport(parent, TEST_CHILD), renderMagmaImport(parent, TEST_CHILD));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testMultipleImports(int count) {
        var input = IntStream.range(0, count)
                .mapToObj(i -> TEST_CHILD + i)
                .map(line -> renderJavaImport(TEST_PARENT, line))
                .collect(Collectors.joining());

        var output = IntStream.range(0, count)
                .mapToObj(i -> TEST_CHILD + i)
                .map(line -> renderMagmaImport(TEST_PARENT, line))
                .collect(Collectors.joining());
        assertCompile(input, output);
    }
}
