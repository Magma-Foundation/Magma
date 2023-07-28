package com.meti.app;

import org.junit.jupiter.api.Test;

import static com.meti.java.JavaString.fromSlice;

class ClassTest extends CompiledTest {
    @Test
    void staticMethod() {
        assertCompile("class Test {static void test(){}}",
                """
                        class def Test() => {
                        }
                        object Tests {
                            def test() : Void => {
                            }
                        }""");
    }

    @Test
    void constructor() {
        assertCompile("class Test{public Test(){}}", """
                class def Test() => {
                }
                                
                object Tests {
                    def new() => {
                        return Test();
                    }
                }""");
    }

    @Test
    void block() {
        assertCompile(fromSlice("{}"), fromSlice("{}"));
    }

    @Test
    void field() {
        assertCompile(fromSlice("class Test {int x;}"),
                fromSlice("class def Test(x : I16) => {}"));
    }

    @Test
    void class_() {
        assertCompile(fromSlice("class Test {}"), fromSlice("class def Test() => {}"));
    }
}