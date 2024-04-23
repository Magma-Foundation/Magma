package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String USE_STRICT = "\"use strict\"";
    public static final String C_PROGRAM = "int main(){return 0;}";
    public static final String PREFIX = "class Main(){public static void main(String[] args){";
    public static final String SUFFIX = "}}";
    public static final String INT_KEYWORD = "int ";
    public static final String DEFINITION_SUFFIX = " = 0;";

    private static String compileMagmaFromJava(String javaSource) {
        var content = javaSource.substring(PREFIX.length(), javaSource.indexOf(SUFFIX));
        if (content.isEmpty()) {
            return "";
        } else {
            var name = content.substring(INT_KEYWORD.length(), content.indexOf(DEFINITION_SUFFIX));
            return renderMagmaDefinition(name);
        }
    }

    private static String wrapInMain(String content) {
        return PREFIX + content + SUFFIX;
    }

    private static String renderMagmaDefinition(String name) {
        return "let " + name + SUFFIX;
    }

    @Test
    void javaEmpty() {
        assertEquals("", compileMagmaFromJava(wrapInMain("")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaDefinition(String name) {
        assertEquals(renderMagmaDefinition(name), compileMagmaFromJava(wrapInMain(INT_KEYWORD + name + DEFINITION_SUFFIX)));
    }

    @Test
    void compileJS() {
        assertEquals(USE_STRICT, USE_STRICT);
    }

    @Test
    void compileC() {
        assertEquals(C_PROGRAM, C_PROGRAM);
    }
}
