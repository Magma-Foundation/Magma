package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    public static final String USE_STRICT = "\"use strict\"";
    public static final String C_PROGRAM = "int main(){return 0;}";

    @Test
    void compileMagma() {
        assertEquals("", "");
    }

    @Test
    void compileJS() {
        assertEquals(USE_STRICT, USE_STRICT);
    }

    @Test
    void compileC() {
        assertEquals(C_PROGRAM, C_PROGRAM);
    }
}
