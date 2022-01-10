package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class VariableTest {
    @Test
    void test() {
        assertSourceCompile("def wrapper(value : I16) : I16 => {return value;}",
                "int wrapper(int value){return value;}");
    }
}
