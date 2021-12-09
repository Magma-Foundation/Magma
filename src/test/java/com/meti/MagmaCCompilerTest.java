package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaCCompilerTest {
    @Test
    void name() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    private void assertCompile(String input, String output) {
        var actual = new MagmaCCompiler(input)
                .compile();
        assertEquals(output, actual);
    }

    @Test
    void type() {
        assertCompile("def main() : U16 => {return 0;}", "unsigned int main(){return 0;}");
    }
}