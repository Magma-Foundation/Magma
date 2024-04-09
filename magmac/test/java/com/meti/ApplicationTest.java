package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String JAVA_CLASS = "class Test {public static void main(String args[]){}}";

    private static String compileMagma(String input) {
        return input;
    }

    private static String compileJava(String input) {
        return input;
    }

    @Test
    void emptyMagma() {
        assertEquals("", compileJava(compileMagma("")));
    }

    @Test
    void emptyJava() {
        assertEquals(JAVA_CLASS, compileMagma(compileJava(JAVA_CLASS)));
    }
}
