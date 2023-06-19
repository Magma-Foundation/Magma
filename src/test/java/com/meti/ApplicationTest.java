package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ApplicationTest {

    private static void assertInterpret(String input, String output) {
        Interpreter interpreter = new Interpreter(new NativeString(input));
        try {
            assertEquals(output, interpreter.interpret1().unwrap().unwrap());
        } catch (InterpretationError e) {
            fail(e);
        }
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
