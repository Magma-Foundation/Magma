package com.meti;

import org.junit.jupiter.api.Test;

public class DefinitionTest extends FeatureTest {
    private static void assertDefinition(String input, String output) {
        assertCompile("class Test {" + input + "}",
                "class def Test() => {\n" +
                "\t" + output + "\n" +
                "}");
    }

    @Test
    void simple() {
        assertDefinition("int value = 0;", "let value : I32 = 0;");
    }

    @Test
    void name() {
        assertDefinition("int test = 0;", "let test : I32 = 0;");
    }

    @Test
    void type() {
        assertDefinition("long value = 0;", "let value : I64 = 0;");
    }

    @Test
    void value() {
        assertDefinition("int value = 100;", "let value : I32 = 100;");
    }

    @Test
    void finalKeyword() {
        assertDefinition("final int value = 100;", "const value : I32 = 100;");
    }

    @Test
    void publicKeyword() {
        assertDefinition("public final int value = 100;", "pub const value : I32 = 100;");
    }
}
