package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String SIMPLEST_JAVA = "class Test {public static void main(String[] args){}}";

    private static String compileJavaFromMagma(String magmaInput) {
        return magmaInput;
    }

    private static String compileMagmaFromJava(String javaInput) {
        return javaInput;
    }

    @Test
    void smallestMagma() {
        assertEquals("", compileMagmaFromJava(compileJavaFromMagma("")));
    }

    @Test
    void smallestJava() {
        assertEquals(SIMPLEST_JAVA, compileJavaFromMagma(compileMagmaFromJava(SIMPLEST_JAVA)));
    }
}
