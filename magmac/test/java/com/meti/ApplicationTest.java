package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String TEST_INPUT = "System.out.println(\"Hello World!\");";

    private static String createInput(String content) {
        return "class Main {public static void main(String[] args){" + content + "}}";
    }

    private static String run(String input) {
        var prefix = "class Main {public static void main(String[] args){";
        var suffix = "}}";

        if(!input.startsWith(prefix)) return "";
        if(!input.endsWith(suffix)) return "";

        return input.substring(prefix.length(), input.length() - suffix.length());
    }

    @Test
    void empty() {
        assertEquals("", run(createInput("")));
    }

    @ParameterizedTest
    @ValueSource(strings = {TEST_INPUT, "System.out.println(\"Test!\");"})
    void print(String input) {
        assertEquals(input, run(createInput(input)));


    }
}
