package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String USE_STRICT = "\"use strict\"\n";
    public static final String PREFIX = "class Main(){public static void main(String[] args){";
    public static final String SUFFIX = "}}";
    public static final String INT_TYPE = "int";
    public static final String INT_KEYWORD_WITH_SPACE = INT_TYPE + " ";
    public static final String DEFINITION_SUFFIX = " = 0;";
    public static final String LET_KEYWORD_WITH_SPACE = "let ";
    public static final String TEST_LOWER_SYMBOL = "test";
    public static final String I16_TYPE = "I16";
    public static final String NUMBER_TYPE = "number";
    public static final String TYPE_SEPARATOR = " : ";
    public static final String LONG_TYPE = "long";
    public static final String I32_TYPE = "I32";

    private static String renderCProgram() {
        return renderCProgram("");
    }

    private static String renderCProgram(String input) {
        return INT_KEYWORD_WITH_SPACE + " main(){" + input + "return 0;}";
    }

    private static String compileMagmaFromJava(String javaSource) {
        var content = javaSource.substring(PREFIX.length(), javaSource.indexOf(SUFFIX));
        if (content.isEmpty()) return "";

        var before = content.substring(0, content.indexOf(DEFINITION_SUFFIX));
        var separator = before.indexOf(' ');
        var name = before.substring(separator + 1);
        var inputType = before.substring(0, separator);
        String outputType;
        if(inputType.equals(INT_TYPE)) {
            outputType = I16_TYPE;
        } else if(inputType.equals(LONG_TYPE)) {
            outputType = I32_TYPE;
        } else {
            throw new UnsupportedOperationException("Unknown type: " + inputType);
        }

        return renderTSDefinition(name, outputType);
    }

    private static String wrapInMain(String content) {
        return PREFIX + content + SUFFIX;
    }

    private static String renderTSDefinition(String name, String type) {
        return LET_KEYWORD_WITH_SPACE + name + TYPE_SEPARATOR + type + DEFINITION_SUFFIX;
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

    private static String renderJavaDefinition(String type, String name) {
        return type + " " + name + DEFINITION_SUFFIX;
    }

    private static String compileCFromMagma(String input) {
        String output;
        if (input.isEmpty()) {
            output = "";
        } else {
            var name = input.substring(LET_KEYWORD_WITH_SPACE.length(), input.indexOf(TYPE_SEPARATOR));
            output = renderJavaDefinition(INT_TYPE, name);
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
        assertEquals(renderTSDefinition(name, I16_TYPE), compileMagmaFromJava(wrapInMain(renderJavaDefinition(INT_TYPE, name))));
    }

    @Test
    void javaDefinitionType() {
        assertEquals(renderTSDefinition(TEST_LOWER_SYMBOL, I32_TYPE), compileMagmaFromJava(wrapInMain(renderJavaDefinition(LONG_TYPE, TEST_LOWER_SYMBOL))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void tsDefinition(String name) {
        assertEquals(USE_STRICT + renderTSDefinition(name, I16_TYPE), compileTSFromMagma(renderTSDefinition(name, I16_TYPE)));
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
        assertEquals(renderCProgram(renderJavaDefinition(INT_TYPE, name)), compileCFromMagma(renderTSDefinition(name, I16_TYPE)));
    }
}
