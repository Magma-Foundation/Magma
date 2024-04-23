package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String USE_STRICT = "\"use strict\"\n";
    public static final String PREFIX = "class Main(){public static void main(String[] args){";
    public static final String SUFFIX = "}}";
    public static final String INT_KEYWORD = "int ";
    public static final String DEFINITION_SUFFIX = " = 0;";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";

    private static String renderCProgram() {
        return renderCProgram("");
    }

    private static String renderCProgram(String input) {
        return "int main(){" + input + "return 0;}";
    }

    private static String compileMagmaFromJava(String javaSource) {
        var content = javaSource.substring(PREFIX.length(), javaSource.indexOf(SUFFIX));
        if (content.isEmpty()) {
            return "";
        } else {
            var name = content.substring(INT_KEYWORD.length(), content.indexOf(DEFINITION_SUFFIX));
            return renderTSDefinition(name);
        }
    }

    private static String wrapInMain(String content) {
        return PREFIX + content + SUFFIX;
    }

    private static String renderTSDefinition(String name) {
        return LET_KEYWORD_WITH_SPACE + name + SUFFIX;
    }

    private static String compileTSFromMagma(String input) {
        String output;
        if (input.isEmpty()) {
            output = "";
        } else {
            output = input;
        }
        return USE_STRICT + output;
    }

    private static String renderJavaDefinition(String name) {
        return INT_KEYWORD + name + DEFINITION_SUFFIX;
    }

    private static String compileCFromMagma(String input) {
        String output;
        if (input.isEmpty()) {
            output = "";
        } else {
            var name = input.substring(LET_KEYWORD_WITH_SPACE.length(), input.indexOf(SUFFIX));
            output = renderJavaDefinition(name);
        }
        return renderCProgram(output);
    }

    @Test
    void java() {
        assertEquals("", compileMagmaFromJava(wrapInMain("")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaDefinition(String name) {
        assertEquals(renderTSDefinition(name), compileMagmaFromJava(wrapInMain(renderJavaDefinition(name))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void tsDefinition(String name) {
        assertEquals(USE_STRICT + renderTSDefinition(name), compileTSFromMagma(renderTSDefinition(name)));
    }

    @Test
    void ts() {
        assertEquals(USE_STRICT, compileTSFromMagma(""));
    }

    @Test
    void c() {
        assertEquals(renderCProgram(), compileCFromMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void cDefinition(String name) {
        assertEquals(renderCProgram(renderJavaDefinition(name)), compileCFromMagma(renderTSDefinition(name)));
    }
}
