package com.meti;

import com.meti.node.JavaRenderStage;
import com.meti.node.MagmaRenderStage;
import com.meti.node.MagmaRenderer;
import com.meti.node.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureTest {
    protected static void assertCompile(String input, String expected) {
        var output = new Compiler(input).compile();
        assertEquals(expected, output);
    }

    protected static void assertCompile(Node input, Node output) {
        FeatureTest.assertCompile(
                new JavaRenderStage(input).render().orElseThrow(),
                new MagmaRenderStage(output).render().orElseThrow());
    }
}
