package com.meti.app.feature;

import org.junit.jupiter.api.Test;

class MethodTest extends CompiledTest {
    @Test
    void declarationInMethod() {
        assertCompile("void test(){int x;}", "def test() : Void => {x : I16}");
    }

    @Test
    void methodWithinClass() {
        assertCompile("class Test{void test(){}}", "class def Test() => {def test() : Void => {}}");
    }

    @Test
    void methodParameter() {
        assertCompile("int foo(int bar){}", "def foo(bar : I16) : I16 => {}");
    }

    @Test
    void methodReturns() {
        assertCompile("int foo(){}", "def foo() : I16 => {}");
    }

    @Test
    void methodName() {
        assertCompile("void foo(){}", "def foo() : Void => {}");
    }

    @Test
    void method() {
        assertCompile("void test(){}", "def test() : Void => {}");
    }
}