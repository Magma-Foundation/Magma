package com.meti.app.compile.scope;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertDoesNotCompile;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class VariableTest {
    @Test
    void defined() {
        assertSourceCompile("const x : I16 = 420;x", "int x=420;x");
    }

    @Test
    void resolved() {
        assertSourceCompile("const x = 420; const y = x;", "int x=420;int y=x;");
    }

    @Test
    void test() {
        assertSourceCompile("def wrapper(value : I16) : I16 => {return value;}", "int wrapper(int value){return value;}");
    }

    @Test
    void undefined() {
        assertDoesNotCompile("test");
    }
}
