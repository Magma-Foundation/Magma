package com.meti.app.compile.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertHeaderCompiles;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class FunctionTest {
    @Test
    void nominal() {
        assertSourceCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void parameter_defined() {
        assertSourceCompile("def wrapper(value : I16) => {return value;}",
                "int wrapper(int value){return value;}");
    }

    @Test
    void parameter_multiple() {
        assertSourceCompile("def Point(x : I16, y : I16) : Void => {}", "void Point(int x,int y){}");
    }

    @Test
    void parameter_single() {
        assertSourceCompile("def one_parameter(value : I16) : Void => {}", "void one_parameter(int value){}");
    }

    @Test
    void split() {
        assertHeaderCompiles("def empty() : Void => {}", "void empty();");
    }

    @Test
    void type() {
        assertSourceCompile("def test() : U16 => {return 0;}", "unsigned int test(){return 0;}");
    }

    @Test
    void type_implicit_content() {
        assertSourceCompile("def value() => {return 420;}", "int value(){return 420;}");
    }

    @Test
    void type_implicit_no_content() {
        assertSourceCompile("def empty() => {}", "void empty(){}");
    }
}
