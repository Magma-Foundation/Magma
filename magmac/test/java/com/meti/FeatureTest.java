package com.meti;

import com.meti.magma.MagmaClassNodeBuilder;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.java.JavaLang.renderJavaClass;

public class FeatureTest {
    public static final String TEST_SYMBOL = "Test";

    static void assertWithinClass(String input, String output) {
        assertCompile(renderJavaClass(TEST_SYMBOL, input), new MagmaClassNodeBuilder().withPrefix("").withName(TEST_SYMBOL).withContent(output).build().render());
    }
}
