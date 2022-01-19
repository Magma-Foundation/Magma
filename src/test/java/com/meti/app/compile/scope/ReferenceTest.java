package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class ReferenceTest {
    @Test
    void type() {
        assertSourceCompile("def test(value : &I16) => {}", "void test(int *value){}");
    }
}
