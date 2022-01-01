package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaCCompilerTest {
    @Test
    void empty() {
        assertCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void empty_block() {
        assertCompile("{}", "{}");
    }

    @Test
    void function_type() {
        assertCompile("def empty() : Void => {};def main() : () => Void => {return empty;}",
                "void empty(){}void (*main())(){return empty;}");
    }

    @Test
    void functional_parameter() {
        assertCompile("def consumer(value : () => Void) : Void => {}", "void consumer(void (*value())){}");
    }

    @Test
    void integer() {
        assertCompile("420", "420");
    }

    private void assertCompile(String input, String output) {
        var actual = new MagmaCCompiler(input)
                .compile();
        assertEquals(output, actual);
    }

    @Test
    void multiple() {
        assertCompile("def first() : I16 => {return 0;};def second() : I16 => {return 0;}",
                "int first(){return 0;}int second(){return 0;}");
    }

    @Test
    void name() {
        assertCompile("def supplier() : I16 => {return 0;}", "int supplier(){return 0;}");
    }

    @Test
    void one_parameter() {
        assertCompile("def wrapper(value : I16) : I16 => {return value;}", "int wrapper(int value){return value;}");
    }

    @Test
    void returns() {
        assertCompile("return 420", "return 420;");
    }

    @Test
    void two_parameters() {
        assertCompile("def Point(x : I16, y : I16) : Void => {}", "void Point(int x,int y){}");
    }

    @Test
    void type() {
        assertCompile("def main() : U16 => {return 0;}", "unsigned int main(){return 0;}");
    }
}