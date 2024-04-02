package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.java.RenderableBuilder;
import com.meti.magma.MagmaClassNodeBuilder;

import static com.meti.CompiledTest.assertCompile;

public class FeatureTest {
    public static final String TEST_SYMBOL = "Test";

    static void assertWithinClass(String input, String output) {
        var render = new JavaClassNodeBuilder().
                withPrefix("")
                .withName(TEST_SYMBOL)
                .withContent(input)
                .build()
                .render();
        var render1 = new MagmaClassNodeBuilder()
                .withName(TEST_SYMBOL)
                .withContent("\n\t" + output)
                .build()
                .render();
        assertCompile(render, render1);
    }

    static void assertWithinClass(RenderableBuilder input, RenderableBuilder output) {
        assertWithinClass(input.build().render(), output.build().render());
    }
}
