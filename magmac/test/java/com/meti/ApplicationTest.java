package com.meti;

import com.meti.collect.JavaString;
import com.meti.compile.Compiler;
import com.meti.compile.Lang;
import com.meti.node.MapNodePrototype;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.compile.Compiler.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String TEST_LOWER_SYMBOL = "test";

    private static void assertRun(String input, String output) {
        JavaString input1 = new JavaString(input);
        assertEquals(output, run(input1).value());
    }

    static String renderBeforeClass(String input) {
        return input + Compiler.renderJavaClass(TEST_UPPER_SYMBOL);
    }

    private static void assertRunWithinClass(String input, String output) {
        assertRun(Compiler.renderJavaClass(emptyList(), TEST_UPPER_SYMBOL, input),
                Compiler.renderMagmaFunctionUnsafe(createFunctionNode(Collections.emptyList(), TEST_UPPER_SYMBOL, output)));
    }

    static String renderBeforeFunction(String before) {
        return before + renderMagmaFunction();
    }

    static String renderMagmaFunction() {
        return renderMagmaFunctionUnsafe(new MapNodePrototype()
                .withString("name", new JavaString(TEST_UPPER_SYMBOL))
                .withString("content", new JavaString(""))
                .complete(new JavaString("function")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertRunWithinClass(renderJavaDefinition(new MapNodePrototype()
                .withListOfStrings("modifiers", emptyList())
                .withString("type", new JavaString(Lang.INT_KEYWORD))
                .withString("name", new JavaString(name))
                .withString("value", new JavaString("0"))
                .complete(new JavaString("definition"))), renderMagmaDefinitionUnsafe(new MapNodePrototype()
                .withListOfStrings("segments", computeSegments(emptyList(), Lang.LET_KEYWORD_WITH_SPACE, name))
                .withString("type", new JavaString(Lang.I32_KEYWORD))
                .withString("value", new JavaString("0"))
                .complete(new JavaString("definition"))));
    }

    @Test
    void definitionType() {
        assertRunWithinClass(renderJavaDefinition(new MapNodePrototype()
                .withListOfStrings("modifiers", emptyList())
                .withString("type", new JavaString(Lang.LONG_KEYWORD))
                .withString("name", new JavaString(TEST_LOWER_SYMBOL))
                .withString("value", new JavaString("0"))
                .complete(new JavaString("definition"))), renderMagmaDefinitionUnsafe(new MapNodePrototype()
                .withListOfStrings("segments", computeSegments(emptyList(), Lang.LET_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL))
                .withString("type", new JavaString(Lang.I64_KEYWORD))
                .withString("value", new JavaString("0"))
                .complete(new JavaString("definition"))));
    }

    @Test
    void definitionValue() {
        var value = "100";
        assertRunWithinClass(renderJavaDefinition(new MapNodePrototype()
                        .withListOfStrings("modifiers", emptyList())
                        .withString("type", new JavaString(Lang.LONG_KEYWORD))
                        .withString("name", new JavaString(TEST_LOWER_SYMBOL))
                        .withString("value", new JavaString(value))
                        .complete(new JavaString("definition"))),
                renderMagmaDefinitionUnsafe(new MapNodePrototype()
                        .withListOfStrings("segments", computeSegments(emptyList(), Lang.LET_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL))
                        .withString("type", new JavaString(Lang.I64_KEYWORD))
                        .withString("value", new JavaString(value))
                        .complete(new JavaString("definition"))));
    }

    @Test
    void definitionPublic() {
        JavaString modifierString = new JavaString(Lang.PUBLIC_KEYWORD);
        assertRunWithinClass(renderJavaDefinition(new MapNodePrototype()
                        .withListOfStrings("modifiers", Collections.singletonList(new JavaString(Lang.PUBLIC_KEYWORD)))
                        .withString("type", new JavaString(Lang.LONG_KEYWORD))
                        .withString("name", new JavaString(TEST_LOWER_SYMBOL))
                        .withString("value", new JavaString("0"))
                        .complete(new JavaString("definition"))),
                renderMagmaDefinitionUnsafe(new MapNodePrototype()
                        .withListOfStrings("segments", computeSegments(singletonList(modifierString), Lang.LET_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL))
                        .withString("type", new JavaString(Lang.I64_KEYWORD))
                        .withString("value", new JavaString("0"))
                        .complete(new JavaString("definition"))));
    }

    @Test
    void definitionConst() {
        assertRunWithinClass(renderJavaDefinition(new MapNodePrototype()
                        .withListOfStrings("modifiers", Collections.singletonList(new JavaString(Lang.FINAL_KEYWORD)))
                        .withString("type", new JavaString(Lang.LONG_KEYWORD))
                        .withString("name", new JavaString(TEST_LOWER_SYMBOL))
                        .withString("value", new JavaString("0"))
                        .complete(new JavaString("definition"))),
                renderMagmaDefinitionUnsafe(new MapNodePrototype()
                        .withListOfStrings("segments", computeSegments(Collections.emptyList(), Lang.CONST_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL))
                        .withString("type", new JavaString(Lang.I64_KEYWORD))
                        .withString("value", new JavaString("0"))
                        .complete(new JavaString("definition"))));
    }

    @Test
    void definitionStatic() {
        final String content = renderMagmaDefinitionUnsafe(new MapNodePrototype()
                .withListOfStrings("segments", computeSegments(Collections.emptyList(), Lang.LET_KEYWORD_WITH_SPACE, TEST_LOWER_SYMBOL))
                .withString("type", new JavaString(Lang.I64_KEYWORD))
                .withString("value", new JavaString("0"))
                .complete(new JavaString("definition")));
        assertRun(Compiler.renderJavaClass(emptyList(), TEST_UPPER_SYMBOL, renderJavaDefinition(new MapNodePrototype()
                        .withListOfStrings("modifiers", Collections.singletonList(Lang.STATIC_STRING))
                        .withString("type", new JavaString(Lang.LONG_KEYWORD))
                        .withString("name", new JavaString(TEST_LOWER_SYMBOL))
                        .withString("value", new JavaString("0"))
                        .complete(new JavaString("definition")))),
                Compiler.renderMagmaFunctionUnsafe(createFunctionNode(Collections.emptyList(), TEST_UPPER_SYMBOL, "")) + renderObjectUnsafe(
                        new MapNodePrototype()
                                .withString("name", new JavaString(TEST_UPPER_SYMBOL))
                                .withString("content", new JavaString(content))
                                .complete(new JavaString("object"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void importChildren(String child) {
        assertRun(renderBeforeClass(renderJavaImport(TEST_LOWER_SYMBOL, child)), renderBeforeFunction(renderMagmaImportUnsafe(new MapNodePrototype()
                .withString("parent", new JavaString(TEST_LOWER_SYMBOL))
                .withString("child", new JavaString(child))
                .complete(new JavaString("import")))));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void importMultiple(int count) {
        var inputBefore = IntStream.range(0, count)
                .mapToObj(index -> renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL + index))
                .collect(Collectors.joining());

        var outputBefore = IntStream.range(0, count)
                .mapToObj(index -> renderMagmaImportUnsafe(new MapNodePrototype()
                        .withString("parent", new JavaString(TEST_LOWER_SYMBOL))
                        .withString("child", new JavaString(TEST_UPPER_SYMBOL + index))
                        .complete(new JavaString("import"))))
                .collect(Collectors.joining());

        assertRun(renderBeforeClass(inputBefore), renderBeforeFunction(outputBefore));
    }

    @Test
    void importStatic() {
        var input = renderBeforeClass(renderJavaImport(TEST_LOWER_SYMBOL, TEST_UPPER_SYMBOL, Lang.STATIC_KEYWORD_WITH_SPACE));
        var output = renderBeforeFunction(renderMagmaImportUnsafe(new MapNodePrototype()
                .withString("parent", new JavaString(TEST_LOWER_SYMBOL))
                .withString("child", new JavaString(TEST_UPPER_SYMBOL))
                .complete(new JavaString("import"))));
        assertRun(input, output);
    }

    @Test
    void importParent() {
        var otherParent = "foo";
        assertRun(renderBeforeClass(renderJavaImport(otherParent, TEST_UPPER_SYMBOL)), renderBeforeFunction(renderMagmaImportUnsafe(new MapNodePrototype()
                .withString("parent", new JavaString(otherParent))
                .withString("child", new JavaString(TEST_UPPER_SYMBOL))
                .complete(new JavaString("import")))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void packageStatement(String name) {
        assertRun(renderBeforeClass("package " + name + Lang.STATEMENT_END), renderMagmaFunction());
    }

    @Test
    void classPublic() {
        var input = renderJavaClass(Lang.PUBLIC_KEYWORD, TEST_UPPER_SYMBOL);
        final java.util.List<JavaString> ts = Collections.singletonList(new JavaString(Lang.EXPORT_KEYWORD));
        var output = renderMagmaFunctionUnsafe(createFunctionNode(ts, TEST_UPPER_SYMBOL, ""));
        assertRun(input, output);
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void className(String name) {
        assertRun(Compiler.renderJavaClass(name), renderMagmaFunctionUnsafe(new MapNodePrototype()
                .withString("name", new JavaString(name))
                .withString("content", new JavaString(""))
                .complete(new JavaString("function"))));
    }
}
