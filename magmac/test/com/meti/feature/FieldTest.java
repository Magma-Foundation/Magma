package com.meti.feature;

import com.meti.FeatureTest;
import org.junit.jupiter.api.Test;

public class FieldTest extends FeatureTest {
    @Test
    void simple() {
        assertField("a.b", "a.b");
    }

    @Test
    void caller(){
        assertField("c.b", "c.b");
    }

    @Test
    void member(){
        assertField("a.d", "a.d");
    }

    private static void assertField(String input, String output) {
        assertCompile("class Test {String value = " + input + ";}",
                "class def Test() => {\n" +
                "\tlet value : String = " + output + ";\n" +
                "}");
    }
}
