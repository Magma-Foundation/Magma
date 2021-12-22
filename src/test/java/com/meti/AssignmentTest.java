package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.CompiledTest.assertDoesNotCompile;

public class AssignmentTest {
    @Test
    void undefined() {
        assertDoesNotCompile("x = 420;");
    }

    @Test
    void valid() {
        assertCompile("let x = 420;x = 69;", "int x=420;x=69;");
    }

    @Test
    void type() {
        assertDoesNotCompile("let x = 420;x ='a';");
    }
}
