package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    @Test
    void empty() {
        assertCompile("", "");
    }

    @Test
    void name() {
        assertCompile("def empty() : I16 => {return 0;}", "int empty(){return 0;}");
    }

    private static void assertCompile(String input, String output) {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }

    @Test
    void testMain() {
        assertCompile("def main() : I16 => {return 0;}", "int main(){return 0;}");
    }
}
