package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImportFeatureTest {
    public static final String TEST_PARENT = "org.junit.jupiter.api";
    public static final String TEST_NAME = "Test";

    private static String renderJavaImport(String parent, String child) {
        return renderJavaImport("", parent, child);
    }

    private static String renderJavaImport(String staticString, String parent, String child) {
        return Lang.IMPORT_KEYWORD + staticString + parent + "." + child + ";";
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleImports(int count) {
        var input = renderJavaImport(TEST_PARENT, TEST_NAME).repeat(count);
        var output = IntStream.range(0, count)
                .mapToObj(_i -> new ImportNode(TEST_PARENT, "{ " + TEST_NAME + " } from ").render())
                .collect(Collectors.joining("\n"));

        CompiledTest.assertCompile(input, output);
    }

    @Test
    void testWhitespace() {
        CompiledTest.assertCompile("\t" + renderJavaImport(TEST_PARENT, TEST_NAME) + "\t", new ImportNode(TEST_PARENT, "{ " + TEST_NAME + " } from ").render());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AfterEach", "Test"})
    void testSimpleImports(String name) {
        CompiledTest.assertCompile(renderJavaImport(TEST_PARENT, name), new ImportNode(TEST_PARENT, "{ " + name + " } from ").render());
    }

    @Test
    void testImportsWithParent() {
        var otherParent = "Parent";
        CompiledTest.assertCompile(renderJavaImport(otherParent, TEST_NAME), new ImportNode(otherParent, "{ " + TEST_NAME + " } from ").render());
    }

    @Test
    void testImportsForAll() {
        ImportNode importNode = new ImportNode(TEST_PARENT, "");
        CompiledTest.assertCompile(renderJavaImport(Lang.STATIC_KEYWORD, ImportFeatureTest.TEST_PARENT, "*"), importNode.render());
    }
}
