package com.meti;

import org.junit.jupiter.api.Test;

public class MethodTest extends CompiledTest {
    @Test
    void test() {
        assertCompile("void empty(){}", "def empty() : Void => {}");
    }
}
