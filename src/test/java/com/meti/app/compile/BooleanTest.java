package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class BooleanTest {
    @Test
    void declared(){
        assertSourceCompile("let x : I16 = false", "int x=0;");
    }

    @Test
    void test_true(){
        assertSourceCompile("true", "1");
    }

    @Test
    void test_false(){
        assertSourceCompile("false", "0");
    }
}
