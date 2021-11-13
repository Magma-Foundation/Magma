package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    @Test
    void empty() {
        assertCompile("", "");
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
