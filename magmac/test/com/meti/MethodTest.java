package com.meti;

import org.junit.jupiter.api.Test;

public class MethodTest extends FeatureTest {
    private static void assertMethod(String input, String output) {
        assertCompile("class Test {" + input + "}",
                "class def Test() => {\n" +
                "\t" + output + "\n" +
                "}");
    }

    @Test
    void type() {
        assertMethod("int empty(){}", "def empty() : I32 => {}");
    }

    @Test
    void simple() {
        assertMethod("void empty(){}", "def empty() : Void => {}");
    }

    @Test
    void name() {
        assertMethod("void test(){}", "def test() : Void => {}");
    }
}
