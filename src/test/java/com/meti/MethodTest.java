package com.meti;

import org.junit.jupiter.api.Test;

public class MethodTest extends CompiledTest {
    @Test
    void test() {
        assertCompile(new JavaClass("Test", new Block()),
                new Implementation("Test", new Block()));
    }
}
