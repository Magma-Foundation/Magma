package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertDoesNotCompile;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class AssignTest {
    @Test
    void immutable() {
        assertDoesNotCompile("const x=420;x=69");
    }

    @Test
    void nominal() {
        assertSourceCompile("let x = 420;x = 69;", "int x=420;x = 69;");
    }
}
