package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class ReferenceTest {
    @Test
    void type() {
        assertSourceCompile("def test(value : &I16) => {}", "void test(int *value){}");
    }
}
