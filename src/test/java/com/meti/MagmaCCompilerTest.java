package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagmaCCompilerTest {
    @Test
    void block() {
        assertCompile("{}", "{}");
    }

    @Test
    void body() {
        assertCompile("def main() : Void => {}", "void main(){}");
    }

    @Test
    void invalid_type() {
        assertThrows(CompileException.class, () -> compileImpl("def test() : test => {}"));
    }

    @Test
    void name() {
        assertCompile("def test() : I16 => {return 0;}", "int test(){return 0;}");
    }

    @Test
    void separator() {
        assertCompile("{};{}", "{}{}");
    }

    @Test
    void no_separator() {
        assertCompile("{}{}", "{}{}");
    }

    @Test
    void inner() {
        assertCompile("{{}}", "{{}}");
    }

    private void assertCompile(String input, String output) {
        try {
            var actual = compileImpl(input);
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    private String compileImpl(String input) throws CompileException {
        return new MagmaCCompiler(input).compile();
    }

    @Test
    void type() {
        assertCompile("def main() : U16 => {return 0;}", "unsigned int main(){return 0;}");
    }
}