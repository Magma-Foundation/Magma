package com.meti.app;

import com.meti.java.String_;
import org.junit.jupiter.api.Test;

import static com.meti.core.Results.unwrap;
import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    private static void assertEmptyOutput(String_ input) {
        assertCompile(input, Empty);
    }

    private static void assertCompile(String input, String output) {
        assertCompile(fromSlice(input), fromSlice(output));
    }

    private static void assertCompile(String_ input, String_ output) {
        try {
            var actual = unwrap(new Compiler(input).compile());
            assertEquals(output.unwrap(), actual.unwrap());
        } catch (CompileException e) {
            fail(e);
        }
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
    void declarationKeyword() {
        assertCompile("private int x", "x : I16");
    }

    @Test
    void declaration() {
        assertCompile("int x", "x : I16");
    }

    @Test
    void declarationName() {
        assertCompile("int test", "test : I16");
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

    @Test
    void importStatic() {
        assertCompile(fromSlice("import static foo.bar"), fromSlice("import { bar } from foo;\n"));
    }

    @Test
    void importMultiple() {
        assertCompile(fromSlice("import foo.bar;import foo.bas"),
                fromSlice("""
                        import { bar } from foo;
                        import { bas } from foo;
                        """));
    }

    @Test
    void import_() {
        assertCompile(fromSlice("import foo.bar"), fromSlice("import { bar } from foo;\n"));
    }

    @Test
    void package_() {
        assertEmptyOutput(fromSlice("package test"));
    }

    @Test
    void empty() {
        assertEmptyOutput(Empty);
    }
}