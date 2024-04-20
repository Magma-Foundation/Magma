package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";

    private static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    private static String renderMagmaFunction(String modifierString, String name) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_CONTENT;
    }

    private static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    private static String renderJavaClass(String modifierString, String name) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + name + CLASS_CONTENT;
    }

    private static String compile(String input) {
        var separator = input.indexOf(';');
        if (separator == -1) {
            return compileClass(input);
        } else {
            var classString = input.substring(separator + 1);
            return compileClass(classString);
        }
    }

    private static String compileClass(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var nameEnd = input.indexOf(CLASS_CONTENT);
        var name = input.substring(nameStart, nameEnd);

        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        return renderMagmaFunction(modifierString, name);
    }

    private static void assertCompile(String input, String output) {
        assertEquals(output, compile(input));
    }

    private static String renderPackageStatement() {
        return "package test;";
    }

    @Test
    void packageStatement() {
        assertCompile(renderPackageStatement() + renderJavaClass(TEST_UPPER_SYMBOL), renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    @Test
    void publicClass() {
        assertCompile(renderJavaClass(PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), renderMagmaFunction(EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleClass(String name) {
        assertCompile(renderJavaClass(name), renderMagmaFunction(name));
    }
}
