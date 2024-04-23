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
    public static final String I32_TYPE = "I32";
    public static final String TYPE_SEPARATOR = " : ";
    public static final String LONG_TYPE = "long";
    public static final String I64_TYPE = "I64";
    public static final String NUMBER_TYPE = "number";

    private static String renderCProgram() {
        return renderCProgram("");
    }

    private static String renderCProgram(String input) {
        return INT_KEYWORD_WITH_SPACE + "main(){" + input + "return 0;}";
    }

    private static String compileMagmaFromJava(String javaSource) {
        var content = javaSource.substring(PREFIX.length(), javaSource.indexOf(SUFFIX));
        if (content.isEmpty()) return "";

        var before = content.substring(0, content.indexOf(DEFINITION_SUFFIX));
        var separator = before.indexOf(' ');
        var name = before.substring(separator + 1);
        var inputType = before.substring(0, separator);
        var outputType = compileJavaType(inputType);

        return renderTSDefinition(name, outputType);
    }

    private static String compileJavaType(String inputType) {
        String outputType;
        if (inputType.equals(INT_TYPE)) {
            outputType = I32_TYPE;
        } else if (inputType.equals(LONG_TYPE)) {
            outputType = I64_TYPE;
        } else {
            throw new UnsupportedOperationException("Unknown type: " + inputType);
        }
        return outputType;
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
            var result = lexMagmaDefinition(input);
            var inputType = result.type();
            var outputType = compileMagmaToJSType(inputType);
            output = renderTSDefinition(result.name(), outputType);
        }
        return formatJS(output);
    }

    private static String compileMagmaToJSType(String inputType) {
        String outputType1;
        if (inputType.equals(I32_TYPE) || inputType.equals(I64_TYPE)) {
            outputType1 = NUMBER_TYPE;
        } else {
            throw new UnsupportedOperationException("Unknown input type: " + inputType);
        }
        return outputType1;
    }

    private static String formatJS(String output) {
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
            var result = lexMagmaDefinition(input);
            var outputType = compileMagmaToCType(result.type());
            output = renderJavaDefinition(outputType, result.name());
        }
        return renderCProgram(output);
    }

    private static MagmaDefinition lexMagmaDefinition(String input) {
        var typeSeparatorIndex = input.indexOf(TYPE_SEPARATOR);
        var name = input.substring(LET_KEYWORD_WITH_SPACE.length(), typeSeparatorIndex);
        var inputType = input.substring(typeSeparatorIndex + TYPE_SEPARATOR.length(), input.indexOf(DEFINITION_SUFFIX));
        return new MagmaDefinition(name, inputType);
    }

    private static String compileMagmaToCType(String inputType) {
        String outputType;
        if (inputType.equals(I32_TYPE)) {
            outputType = INT_TYPE;
        } else if (inputType.equals(I64_TYPE)) {
            outputType = LONG_TYPE;
        } else {
            throw new UnsupportedOperationException("Unknown input type: " + inputType);
        }
        return outputType;
    }

    @Test
    void java() {
        assertEquals("", compileMagmaFromJava(wrapInMain("")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaDefinition(String name) {
        assertEquals(renderTSDefinition(name, I32_TYPE), compileMagmaFromJava(wrapInMain(renderJavaDefinition(INT_TYPE, name))));
    }

    @Test
    void javaDefinitionType() {
        assertEquals(renderTSDefinition(TEST_LOWER_SYMBOL, I64_TYPE), compileMagmaFromJava(wrapInMain(renderJavaDefinition(LONG_TYPE, TEST_LOWER_SYMBOL))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void tsDefinition(String name) {
        assertEquals(formatJS(renderTSDefinition(name, NUMBER_TYPE)), compileTSFromMagma(renderTSDefinition(name, I32_TYPE)));
    }

    @Test
    void tsDefinitionType() {
        assertEquals(formatJS(renderTSDefinition(TEST_LOWER_SYMBOL, NUMBER_TYPE)), compileTSFromMagma(renderTSDefinition(TEST_LOWER_SYMBOL, I64_TYPE)));
    }

    @Test
    void ts() {
        assertEquals(formatJS(""), compileTSFromMagma(""));
    }

    @Test
    void c() {
        assertEquals(renderCProgram(), compileCFromMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void cDefinition(String name) {
        assertEquals(renderCProgram(renderJavaDefinition(INT_TYPE, name)), compileCFromMagma(renderTSDefinition(name, I32_TYPE)));
    }

    @Test
    void cDefinitionType() {
        assertEquals(renderCProgram(renderJavaDefinition(LONG_TYPE, TEST_LOWER_SYMBOL)), compileCFromMagma(renderTSDefinition(TEST_LOWER_SYMBOL, I64_TYPE)));
    }
}
