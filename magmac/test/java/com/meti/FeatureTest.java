package com.meti;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.JavaLang.renderJavaClass;

public class FeatureTest {
    public static final String TEST_SYMBOL = "Test";

    static void assertWithinClass(String input, String output) {
        assertCompile(renderJavaClass(TEST_SYMBOL, input), new MagmaClassNodeBuilder().setPrefix("").setName(TEST_SYMBOL).setContent(output).createMagmaClassNode().render());
    }
}
