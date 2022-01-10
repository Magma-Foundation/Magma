package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class FunctionTest {
    @Test
    void empty(){
        assertCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void return_type() {
        assertCompile("def test() : U16 => {return 0;}", "unsigned int test(){return 0;}");
    }
}
