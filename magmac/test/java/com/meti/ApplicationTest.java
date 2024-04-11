package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    public static final String TEST_UPPER_SYMBOL = "Main";
    public static final String CLASS_KEYWORD = "class ";
    public static final String JAVA_CLASS_BODY = " {}";
    public static final String DEF_KEYWORD = "def ";
    public static final String MAGMA_FUNCTION_BODY = "() => {}";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String PUBLIC_KEYWORD = "public ";

    private static String renderJavaClass(String publicString, String name) {
        return publicString + CLASS_KEYWORD + name + JAVA_CLASS_BODY;
    }

    private static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    private static String renderMagmaFunction(String exportString, String name) {
        return exportString + CLASS_KEYWORD + DEF_KEYWORD + name + MAGMA_FUNCTION_BODY;
    }

    private static String compileJavaToMagma(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD);
        if (classIndex == -1) throw createUnknownInputError(input);

        var name = input.substring(classIndex + CLASS_KEYWORD.length(), input.indexOf(JAVA_CLASS_BODY));
        var exportString = input.startsWith(PUBLIC_KEYWORD) ? EXPORT_KEYWORD : "";

        return renderMagmaFunction(exportString, name);
    }

    private static CompileException createUnknownInputError(String input) {
        return new CompileException("Unknown input: " + input);
    }

    private static String compileMagmaToJava(String input) throws CompileException {
        var prefixIndex = input.indexOf(CLASS_KEYWORD + DEF_KEYWORD);
        if (prefixIndex == -1) throw createUnknownInputError(input);

        var name = input.substring(prefixIndex + (CLASS_KEYWORD + DEF_KEYWORD).length(), input.indexOf(MAGMA_FUNCTION_BODY));
        var publicString = input.startsWith(EXPORT_KEYWORD) ? PUBLIC_KEYWORD : "";

        return renderJavaClass(publicString, name);
    }

    private static void assertMagma(String input) {
        try {
            assertEquals(input, compileJavaToMagma(compileMagmaToJava(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static void assertJava(String input) {
        try {
            assertEquals(input, compileMagmaToJava(compileJavaToMagma(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @Test
    void magmaInvalid() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void magmaSimple(String name) {
        assertMagma(renderMagmaFunction(name));
    }

    @Test
    void magmaPublic() {
        assertMagma(renderMagmaFunction(EXPORT_KEYWORD, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        assertJava(renderJavaClass("", name));
    }

    @Test
    void javaPublic() {
        assertJava(renderJavaClass(PUBLIC_KEYWORD, TEST_UPPER_SYMBOL));
    }
}
