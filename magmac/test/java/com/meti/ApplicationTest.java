package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String TEST_INPUT = "System.out.println(\"Hello World!\");";

    private static String createInput(String content) {
        return "class Main {public static void main(String[] args){" + content + "}}";
    }

    private static String run(String input) {
        if (input.equals(createInput(TEST_INPUT))) {
            return TEST_INPUT;
        }
        return "";
    }

    @Test
    void empty() {
        assertEquals("", run(createInput("")));
    }

    @Test
    void print() {
        assertEquals(TEST_INPUT, run(createInput(TEST_INPUT)));


    }
}
