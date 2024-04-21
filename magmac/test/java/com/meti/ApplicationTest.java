package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";
    public static final String TEST_UPPER_SYMBOL = "Test";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";
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
        var lines = input.split(";");

        var rootImportsWithChildren = new HashMap<String, List<String>>();
        var rootImports = new HashSet<String>();

        for (int i = 0; i < lines.length - 1; i++) {
            String line = lines[i];
            var option = compileStatement(line);
            if (option.isPresent()) {
                var value = option.get();
                if (value instanceof MagmaImport node) {
                    var parent = node.parent();
                    var child = node.child();

                    if (!rootImportsWithChildren.containsKey(parent)) {
                        rootImportsWithChildren.put(parent, new ArrayList<>());
                    }

                    rootImportsWithChildren.get(parent).addAll(child);
                } else if (value instanceof Import node) {
                    rootImports.add(node.name());
                } else {
                    throw new UnsupportedOperationException("Unknown node type: " + value.getClass());
                }
            }
        }

        var parentSet = new HashSet<>(rootImportsWithChildren.keySet());
        parentSet.addAll(rootImports);

        var combinedLines = parentSet.stream()
                .sorted()
                .map(parent -> {
                    var outputImports = new ArrayList<Node>();
                    if (rootImports.contains(parent)) outputImports.add(new Import(parent));
                    if(rootImportsWithChildren.containsKey(parent)) {
                        outputImports.add(new MagmaImport(parent, rootImportsWithChildren.get(parent)));
                    }

                    return outputImports;
                })
                .flatMap(Collection::stream)
                .map(Node::render)
                .collect(Collectors.joining());

        return combinedLines + compileClass(lines[lines.length - 1]);
    }

    private static Optional<Node> compileStatement(String before) {
        if (!before.startsWith(Compiler.IMPORT_KEYWORD_WITH_SPACE)) return Optional.empty();

        var total = before.substring(Compiler.IMPORT_KEYWORD_WITH_SPACE.length());
        var parentSeparator = total.lastIndexOf('.');
        if (parentSeparator == -1) {
            return Optional.of(new Import(total));
        } else {
            var parent = total.substring(0, parentSeparator);
            var child = total.substring(parentSeparator + 1);
            return Optional.of(new MagmaImport(parent, child));
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

    private static String renderPackageStatement(String name) {
        return "package " + name + Compiler.STATEMENT_END;
    }

    private static void assertCompileWithClass(String input, String output) {
        assertCompile(input + renderJavaClass(TEST_UPPER_SYMBOL), output + renderMagmaFunction(TEST_UPPER_SYMBOL));
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
        assertCompile(renderJavaClass(PUBLIC_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL), renderMagmaFunction(EXPORT_KEYWORD_WITH_SPACE, TEST_UPPER_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void simpleClass(String name) {
        assertCompile(renderJavaClass(name), renderMagmaFunction(name));
    }
}
