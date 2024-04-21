package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String TEST_LOWER_SYMBOL = "test";

    private static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    private static String renderJavaClass(String modifierString, String name) {
        return modifierString + Compiler.CLASS_KEYWORD_WITH_SPACE + name + Compiler.CLASS_CONTENT;
    }

    private static void assertCompile(String input, String output) {
        assertEquals(output, Compiler.compile(input));
    }

    private static String renderPackageStatement(String name) {
        return "package " + name + Compiler.STATEMENT_END;
    }

    private static void assertCompileWithClass(String input, String output) {
        assertCompile(input + renderJavaClass(TEST_UPPER_SYMBOL), output + Compiler.renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    private static String renderJavaImport(String parent, String child) {
        return Compiler.renderImport(parent + "." + child);
    }

    @Test
    void multipleImports() {
        assertCompileWithClass(
                renderJavaImport("a", "B") + renderJavaImport("c", "D"),
                new MagmaImport("a", "B").render() + new MagmaImport("c", "D").render()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void siblingImports(int counts) {
        var names = IntStream.range(0, counts)
                .mapToObj(value -> TEST_UPPER_SYMBOL + value)
                .toList();

        var input = names.stream()
                .map(name -> renderJavaImport(TEST_LOWER_SYMBOL, name))
                .collect(Collectors.joining());

        var output = new MagmaImport(TEST_LOWER_SYMBOL, String.join(", ", names)).render();
        assertCompileWithClass(input, output);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleImports(String name) {
        assertCompileWithClass(new Import(name).render(), new Import(name).render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void parentImports(String parentName) {
        assertCompileWithClass(renderJavaImport(parentName, TEST_LOWER_SYMBOL), new MagmaImport(parentName, TEST_LOWER_SYMBOL).render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertCompileWithClass(renderPackageStatement(name), "");
    }

    @Test
    void publicClass() {
        assertCompile(renderJavaClass(Compiler.PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), Compiler.renderMagmaFunction(Compiler.EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleClass(String name) {
        assertCompile(renderJavaClass(name), Compiler.renderMagmaFunction(name));
    }
}
