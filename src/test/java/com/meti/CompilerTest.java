package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void empty() {
        assertCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void function_type() {
        assertCompile("def empty() : Void => {};def main() : () => Void => {return empty;}",
                "void empty(){}void (*main())(){return empty;}");
    }

    private void assertCompile(String input, String output) {
        var actual = new Compiler(input)
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
    void type() {
        assertCompile("def main() : U16 => {return 0;}", "unsigned int main(){return 0;}");
    }
}