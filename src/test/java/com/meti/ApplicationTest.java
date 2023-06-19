package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private static String interpret(String input) {
        if (input.startsWith("let ")) {
            var name = input.substring("let ".length(), input.indexOf('=')).strip();
            var value = input.substring(input.indexOf('=') + 1, input.indexOf(';'));
            return value;
        }

        return interpretValue(input);
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
