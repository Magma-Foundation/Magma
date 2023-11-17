package com.meti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ClassTest extends CompiledTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void test(String name) {
        assertCompile("class " + name + " {}", "class def " + name + "() => {}");
    }
}