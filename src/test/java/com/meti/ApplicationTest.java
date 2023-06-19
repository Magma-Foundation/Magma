package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private static String interpret(String input) {
        var lines = Arrays.stream(input.split(";"))
                .map(String::strip)
                .filter(line -> !line.isEmpty())
                .toList();

        var declarations = new HashMap<String, String>();
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var result = interpretStatement(line, declarations);
            if (i == lines.size() - 1) {
                return result;
            }
        }

        return "";
    }

    private static String interpretStatement(String input, Map<String, String> declarations) {
        if (input.startsWith("let ")) {
            var name = input.substring("let ".length(), input.indexOf('=')).strip();
            var value = input.substring(input.indexOf('=') + 1).strip();
            declarations.put(name, value);
            return null;
        } else if (declarations.containsKey(input)) {
            return declarations.get(input);
        } else {
            return interpretValue(input);
        }
    }

    private static String interpretValue(String input) {
        try {
            return String.valueOf(Integer.parseInt(input.strip()));
        } catch (NumberFormatException e) {
            return "";
        }
    }

    private static void assertInterpret(String input, String output) {
        assertEquals(output, interpret(input));
    }

    @Test
    void multipleDefinitions() {
        assertInterpret("let x=10;let y=20;y", "20");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2"})
    void definitionValue(String value) {
        assertInterpret("let test=" + value + ";test", value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "y", "z"})
    void definitionName(String name) {
        assertInterpret("let " + name + "=0;" + name, "0");
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void padIntegerRight(String padding) {
        assertInterpret("0" + padding, "0");
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void padIntegerLeft(String padding) {
        assertInterpret(padding + "0", "0");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2"})
    void integer(String input) {
        assertInterpret(input, input);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void whitespace(String input) {
        assertInterpret(input, "");
    }

    @Test
    void empty() {
        assertInterpret("", "");
    }
}
