package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String JAVA_CLASS = "class Main {}";
    public static final String MAGMA_CLASS = "class def Main() => {}";

    private static String compileJavaToMagma(String javaClass) {
        return javaClass;
    }

    private static String compileMagmaToJava(String input) {
        return input;
    }

    @Test
    void magmaSimple() {
        assertEquals(MAGMA_CLASS, compileJavaToMagma(compileMagmaToJava(MAGMA_CLASS)));
    }

    @Test
    void javaSimple() {
        assertEquals(JAVA_CLASS, compileMagmaToJava(compileJavaToMagma(JAVA_CLASS)));
    }
}
