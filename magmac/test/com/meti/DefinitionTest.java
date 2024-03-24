package com.meti;

import org.junit.jupiter.api.Test;

public class DefinitionTest extends FeatureTest {
    @Test
    void simple() {
        assertDefinition("int value = 0;", "let value : I32 = 0;");
    }

    @Test
    void name() {
        assertDefinition("int test = 0;", "let test : I32 = 0;");
    }

    private static void assertDefinition(String input, String output) {
        assertCompile("class Test {" + input + "}",
                "class def Test() => {\n" +
                "\t" + output + "\n" +
                "}");
    }
}
