package com.meti;

import org.junit.jupiter.api.Test;

public class ClassTest extends CompiledTest {
    @Test
    void simple() {
        assertCompile(new JavaClass("Test"), new Abstraction("Test"));
    }

    @Test
    void simpleAnother() {
        assertCompile(new JavaClass("Bar"), new Abstraction("Bar"));
    }
}
