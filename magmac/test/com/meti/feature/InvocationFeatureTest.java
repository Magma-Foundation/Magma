package com.meti.feature;

import com.meti.FeatureTest;
import org.junit.jupiter.api.Test;

public class InvocationFeatureTest extends FeatureTest {
    @Test
    void simple() {
        assertInvocation("empty()", "empty()");
    }

    private static void assertInvocation(String input, String output) {
        assertCompile("class Test {String value=" + input + "}",
                "class def Test() => {\n" +
                "\tlet value : String = " + output + ";\n" +
                "}");
    }
}
