package com.meti;

import org.junit.jupiter.api.Test;

public class ClassTest extends CompiledTest {
    @Test
    void simple() {
        assertCompile(new JavaClass("Test", new Block()), new Implementation("Test", false, new Block()));
    }

    @Test
    void simpleAnother() {
        assertCompile(new JavaClass("Bar", new Block()), new Implementation("Bar", false, new Block()));
    }
}
