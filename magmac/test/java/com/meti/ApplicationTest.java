package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String JAVA_CLASS = "class Main {}";

    @Test
    void test() {
        assertEquals(JAVA_CLASS, JAVA_CLASS);
    }
}
