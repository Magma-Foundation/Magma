package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertHeaderCompiles;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class FunctionTest {
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
    void function_type() {
        assertSourceCompile("let x : () => Void;", "void (*x)();");
    }

    @Test
    void function_type_parameters() {
        assertSourceCompile("let x : (I16, U16) => Void", "void (*x)(int,unsigned int);");
    }

    @Test
    void functional_parameters() {
        assertSourceCompile("def doSomething(actor : () => Void) => {}", "void doSomething(void (*actor)()){}");
    }

    @Test
    void implicit() {
        assertSourceCompile("def empty() => {}", "void empty(){}");
    }

    @Test
    void implicit_return() {
        assertSourceCompile("def value() => {return 420;}", "int value(){return 420;}");
    }

    @Test
    void innerFunction() {
        assertSourceCompile("def outer() => {def inner() => {}}",
                "void inner(){}void outer(){}");
    }

    @Test
    void lambda() {
        assertSourceCompile("() => {}", "void __lambda0__(){}__lambda0__");
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
    void split() {
        assertHeaderCompiles("def empty() : Void => {}", "void empty();");
    }

    @Test
    void two_parameters() {
        assertSourceCompile("def Point(x : I16, y : I16) : Void => {}", "void Point(int x,int y){}");
    }
}
