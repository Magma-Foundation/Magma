package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    private static void assertCompile(String input, String output) {
        var actual = new Compiler(input).compile();
        assertEquals(output, actual);
    }

    @Test
    void multiple() {
        assertCompile("import a.B;import c.D", "import { B } from a;import { D } from c;");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void abstractMethod(String name) {
        assertCompile("void " + name + "()", "def " + name + "() : Void");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void returns(String returns) {
        assertCompile(returns + " test()", "def test() : " + returns);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void throwing(String throwing) {
        assertCompile("void test() throws " + throwing, "def test() : Void ? " + throwing);
    }

    @Test
    void block() {
        assertCompile("{}", "{}");
    }

    @Test
    void blockChildren() {
        assertCompile("{void test()}", "{def test() : Void}");
    }

    @Test
    void blockInInterface(){
        assertCompile("interface Test {void test()}", "trait Test {def test() : Void}");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void interfaces(String name) {
        assertCompile("interface " + name + " {}", "trait " + name + " {}");
    }

    @Test
    void publicKeyword(){
        assertCompile("public interface Test {}", "public trait Test {}");
    }
}