package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";
    public static final String DEF_KEYWORD_WITH_SPACE = "def ";
    public static final String MAGMA_FUNCTION_PREFIX = CLASS_KEYWORD_WITH_SPACE + DEF_KEYWORD_WITH_SPACE;
    public static final String FUNCTION_SEPARATOR = "() =>";
    public static final String TEST_LOWER_SYMBOL = "test";

    private static String renderMagmaFunction(String name) {
        return MAGMA_FUNCTION_PREFIX + name + FUNCTION_SEPARATOR + CLASS_CONTENT;
    }

    private static String run(String input, boolean isJava) throws CompileException {
        return getString(input, isJava)
                .entrySet()
                .stream()
                .findFirst()
                .orElseThrow()
                .getValue();
    }

    private static Map<String, String> getString(String input, boolean isJava) throws CompileException {
        if (isJava) {
            return compileMagmaFromJava(input);
        } else {
            return compileJavaFromMagma(input);
        }
    }

    private static Map<String, String> compileMagmaFromJava(String input) throws CompileException {
        var classIndex = input.indexOf(CLASS_KEYWORD_WITH_SPACE);
        if (classIndex == -1) {
            throw new CompileException("No class present.");
        } else {
            var next = input.indexOf(CLASS_KEYWORD_WITH_SPACE, classIndex + CLASS_KEYWORD_WITH_SPACE.length());
            if (next == -1) {
                var name = input.substring(CLASS_KEYWORD_WITH_SPACE.length(), input.indexOf(CLASS_CONTENT));
                return Map.of(name, renderMagmaFunction(name));
            } else {
                throw new CompileException("Multiple classes are not allowed.");
            }
        }
    }

    private static HashMap<String, String> compileJavaFromMagma(String input) throws CompileException {
        var lines = split(input);
        var set = new HashSet<String>();
        var map = new HashMap<String, String>();

        for (String sequence : lines) {
            if (input.startsWith(MAGMA_FUNCTION_PREFIX)) {
                var name = input.substring(MAGMA_FUNCTION_PREFIX.length(), input.indexOf(FUNCTION_SEPARATOR));
                if (set.contains(name)) {
                    throw new CompileException("Two functions cannot have the same name of: " + name);
                } else {
                    set.add(name);
                    map.put(name, renderJavaClass(name));
                }
            } else {
                throw new CompileException("Invalid token sequence: " + sequence);
            }
        }

        return map;
    }

    private static ArrayList<String> split(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == '}' && depth == 1) {
                builder.append(c);
                depth = 0;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        lines.removeIf(String::isBlank);
        return lines;
    }

    private static String renderJavaClass(String name) {
        return CLASS_KEYWORD_WITH_SPACE + name + CLASS_CONTENT;
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileMagmaToJava(String name) throws CompileException {
        assertEquals(renderJavaClass(name), run(renderMagmaFunction(name), false));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> run(renderJavaClass(TEST_LOWER_SYMBOL).repeat(2), true));
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> run(renderMagmaFunction(TEST_LOWER_SYMBOL).repeat(2), false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void compileJavaToMagma(String name) throws CompileException {
        assertEquals(renderMagmaFunction(name), run(renderJavaClass(name), true));
    }
}