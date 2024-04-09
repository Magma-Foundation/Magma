package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    public static final String SIMPLEST_JAVA = "class Test {public static void main(String[] args){}}";

    @Test
    void smallestMagma() {
        assertEquals("", "");
    }

    @Test
    void smallestJava() {
        assertEquals(SIMPLEST_JAVA, SIMPLEST_JAVA);
    }
}
