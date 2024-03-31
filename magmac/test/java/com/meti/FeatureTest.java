package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;

import static com.meti.CompiledTest.assertCompile;

public class FeatureTest {
    public static final String TEST_SYMBOL = "Test";

    static void assertWithinClass(String input, String output) {
        assertCompile(new JavaClassNodeBuilder().setPrefix("").setName(TEST_SYMBOL).setContent(input).createJavaClassNode().renderJavaClass(), new MagmaClassNodeBuilder().withPrefix("").withName(TEST_SYMBOL).withContent(output).build().render());
    }
}
