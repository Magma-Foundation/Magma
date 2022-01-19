package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class VariableTest {
    @Test
    void test() {
        assertSourceCompile("def wrapper(value : I16) : I16 => {return value;}",
                "int wrapper(int value){return value;}");
    }
}
