package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_END = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final String STATEMENT_END = ";";
    public static final String TEST_LOWER_SYMBOL = "test";

    private static String renderMagmaImport(String parent, String child) {
        return IMPORT_KEYWORD_WITH_SPACE + "{ " + child + " } from " + parent + STATEMENT_END;
    }

    private static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    private static String renderMagmaFunction(String modifiersString, String name) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_END;
    }

    private static String run(String input) {
        var lines = Arrays.asList(input.split(";"));
        if (lines.isEmpty()) return "";

        var imports = lines.subList(0, lines.size() - 1);
        var classString = lines.getLast();

        var beforeString = imports.stream()
                .map(ApplicationTest::compileImport)
                .collect(Collectors.joining());

        var compiledClass = compileClass(classString);
        return beforeString + compiledClass;
    }

    private static String compileImport(String beforeString) {
        if (!beforeString.startsWith(IMPORT_KEYWORD_WITH_SPACE)) return "";

        var set = beforeString.substring(IMPORT_KEYWORD_WITH_SPACE.length());
        var last = set.lastIndexOf('.');
        var parent = set.substring(0, last);
        var child = set.substring(last + 1);

        return renderMagmaImport(parent, child);
    }

    private static String compileClass(String input) {
        var classIndex = input.indexOf(CLASS_KEYWORD_WITH_SPACE);
        if (classIndex == -1) throw new UnsupportedOperationException("No class present.");

        var name = input.substring(classIndex + CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_END));
        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";

        return renderMagmaFunction(modifierString, name);
    }

    private static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    private static String renderJavaClass(String modifiersString, String name) {
        return modifiersString + CLASS_KEYWORD_WITH_SPACE + name + CLASS_END;
    }

    private static String renderMagmaFunction() {
        return renderMagmaFunction(TEST_UPPER_SYMBOL);
    }

    private static String renderBeforeClass(String input) {
        return input + renderJavaClass(TEST_UPPER_SYMBOL);
    }

    private static String renderJavaImport(String parent, String child) {
        return IMPORT_KEYWORD_WITH_SPACE + parent + "." + child + STATEMENT_END;
    }

    private static void assertRun(String input, String output) {
        assertEquals(output, run(input));
    }

    private static String renderBeforeFunction(String before) {
        return before + renderMagmaFunction();
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChildren(String child) {
        assertRun(renderBeforeClass(renderJavaImport(TEST_LOWER_SYMBOL, child)), renderBeforeFunction(renderMagmaImport(TEST_LOWER_SYMBOL, child)));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void importMultiple(int count) {
        var inputBefore = IntStream.range(0, count)
                .mapToObj(index -> renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        var outputBefore = IntStream.range(0, count)
                .mapToObj(index -> renderMagmaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        assertRun(renderBeforeClass(inputBefore), renderBeforeFunction(outputBefore));

    }

    @Test
    void importParent() {
        var otherParent = "foo";
        assertRun(renderBeforeClass(renderJavaImport(otherParent, TEST_UPPER_SYMBOL)), renderBeforeFunction(renderMagmaImport(otherParent, TEST_UPPER_SYMBOL)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(renderBeforeClass("package " + name + STATEMENT_END), renderMagmaFunction());
    }

    @Test
    void classPublic() {
        assertRun(renderJavaClass(PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), renderMagmaFunction(EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void className(String name) {
        assertRun(renderJavaClass(name), renderMagmaFunction(name));
    }
}
