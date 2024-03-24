package com.meti.feature;

import com.meti.FeatureTest;
import org.junit.jupiter.api.Test;

public class InvocationFeatureTest extends FeatureTest {
    @Test
    void simple() {
        assertInvocation("empty()", "empty()");
    }

    @Test
    void caller(){
        assertInvocation("test()", "test()");
    }

    @Test
    void oneParameter(){
        assertInvocation("test(1)", "test(1)");
    }

    private static void assertInvocation(String input, String output) {
        assertCompile("class Test {String value=" + input + "}",
                "class def Test() => {\n" +
                "\tlet value : String = " + output + ";\n" +
                "}");
    }
}
