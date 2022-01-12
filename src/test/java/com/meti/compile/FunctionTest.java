package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class FunctionTest {
    @Test
    void implicit(){
        assertSourceCompile("def empty() => {}", "void empty(){}");
    }

    @Test
    void empty() {
        assertSourceCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void external() {
        assertSourceCompile("extern def test() : Void", "");
    }

    @Test
    void external_operator() {
        assertSourceCompile("extern operator def !(state : Bool) : Bool;! false", "!0");
    }

    @Test
    void one_parameter() {
        assertSourceCompile("def one_parameter(value : I16) : Void => {}", "void one_parameter(int value){}");
    }

    @Test
    void return_type() {
        assertSourceCompile("def test() : U16 => {return 0;}", "unsigned int test(){return 0;}");
    }

    @Test
    void two_parameters() {
        assertSourceCompile("def Point(x : I16, y : I16) : Void => {}", "void Point(int x,int y){}");
    }
}
