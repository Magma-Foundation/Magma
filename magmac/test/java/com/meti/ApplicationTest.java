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
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final String STATEMENT_END = ";";
    public static final String TEST_LOWER_SYMBOL = "test";

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
        var statementSeparator = input.indexOf(';');
        if (statementSeparator == -1) return compileClass(input);

        var before = input.substring(0, statementSeparator + 1);
        String newBefore;
        if (before.startsWith(IMPORT_KEYWORD_WITH_SPACE)) {
            var total = before.substring(IMPORT_KEYWORD_WITH_SPACE.length(), before.indexOf(STATEMENT_END));
            var parentSeparator = total.lastIndexOf('.');
            if (parentSeparator == -1) {
                newBefore = renderImportWithNoParent(total);
            } else {
                var parent = total.substring(0, parentSeparator);
                var child = total.substring(parentSeparator + 1);
                newBefore = renderMagmaImport(parent, child);
            }
        } else {
            newBefore = "";
        }

        var classString = input.substring(statementSeparator + 1);
        return newBefore + compileClass(classString);
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

    private static String renderPackageStatement(String name) {
        return "package " + name + STATEMENT_END;
    }

    private static String renderImportWithNoParent(String name) {
        return renderImportWithParent("", name);
    }

    private static String renderImportWithParent(String parentString, String child) {
        return renderImport(parentString + child);
    }

    private static String renderImport(String importString) {
        return IMPORT_KEYWORD_WITH_SPACE + importString + STATEMENT_END;
    }

    private static void assertCompileWithClass(String input, String output) {
        assertCompile(input + renderJavaClass(TEST_UPPER_SYMBOL), output + renderMagmaFunction(TEST_UPPER_SYMBOL));
    }

    private static String renderMagmaImport(String parent, String child) {
        return renderImport("{" + child + "} from " + parent);
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleImports(String name) {
        assertCompileWithClass(renderImportWithNoParent(name), renderImportWithNoParent(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void parentImports(String parentName) {
        assertCompileWithClass(renderImportWithParent(parentName + ".", TEST_LOWER_SYMBOL), renderMagmaImport(parentName, TEST_LOWER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertCompileWithClass(renderPackageStatement(name), "");
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
