package com.meti;

import com.meti.safe.NativeString;
import com.meti.safe.result.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {
    private static void assertInterpret(String input, String output) {
        interpretImpl(input).match(
                actual -> assertEquals(output, actual.internalValue()),
                Assertions::fail);
    }

    private static void assertInterpretError(String input) {
        assertTrue(interpretImpl(input).isErr());
    }

    private static Result<NativeString, InterpretationError> interpretImpl(String input) {
        return new Interpreter(NativeString.from(input)).interpret();
    }

    @Test
    void insideBlock() {
        assertInterpret("let x = 100;{x}", "100");
    }

    @Test
    void outsideBlock() {
        assertInterpretError("{let x = 100;}x");
    }

    @Test
    void emptyBlock() {
        assertInterpret("{}", "");
    }

    @Test
    void assignment() {
        assertInterpret("let x = 10;x=20;x", "20");
    }

    @Test
    void invalidTypes() {
        assertInterpretError("let value : i8 = 10u32");
    }

    @ParameterizedTest
    @ValueSource(strings = {"u8", "u16", "u32", "u64", "i8", "i16", "i32", "i64"})
    void explicitNumber(String suffix) {
        assertInterpret("20" + suffix, "20");
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
